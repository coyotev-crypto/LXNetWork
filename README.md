# LXNetWork
基于OkHttp+Retrofit+RxJava封装的网络框架
自动Model层生成模板代码
使用简单

**1、引入该模块 请引入最新的版本**

```
    implementation 'com.github.lrhcoyote.LXNetWork:network:v1.0.0.5'
    implementation 'com.github.lrhcoyote.LXNetWork:LXAnnotation:v1.0.0.5'
    implementation 'com.github.lrhcoyote.LXNetWork:LXBind:v1.0.0.5'
    annotationProcessor "com.github.lrhcoyote.LXNetWork:LXCompiler:v1.0.0.5"
```

**2、初始化网络在Application中添加**

`HttpClient.init(false);`


**3、创建自己的网络请求接口**

添加@BaseUrl或@TestUrl为你请求的路径

```
@BaseUrl(ApiConstants.MEDICAL_GUIDE_URL)
public interface MedicalGuideApi {

    @POST("/guide/getGuideListByCategoryId")
    Observable<String> getGuideListByCategoryId(@Body RequestBody requestBody);
    
    @GET("guide/v2/{id}")
    Observable<String> getGuideDetDetails(@Path("id") Integer id);
}

@BaseUrl("https://cn.bing.com/")
public interface BingApi {
    @GET("search?form=MOZLBR&pc=MOZI&q=api")
    Observable<String> bingIndex();
}
```

**4、model层的定义**
注意：model里面的方法必须在你的网络请求接口中存在

```
 @LXModel(value = "MedicalGuideModel", networkEngine = MedicalGuideNetwork.class, networkService = MedicalGuideApi.class)
 @LXModelImpl(MedicalGuideModel.class) // MedicalGuideModel 实现被@Ignore忽略的方法
 interface Model {
 
   void getGuideListByCategoryId(RequestBody requestBody, BaseObserver baseObserver);
   
   Observable<String> getGuideDetDetails(Integer id);
   
   @Ignore
   void bingIndex(BaseObserver observer);
 }
 
 
 @LXModel(networkService = BingApi.class)
 interface Model {
   void bingIndex(BaseObserver observer);
 }
```
**注意：如果方法不存在MedicalGuideApi中请添加@Ignore注解。如果你必须要这个方法你可以添加@LXModelImpl注解去实现被@Ignore注解忽略的实现**

**自定义Model实现如下：**

```
public class MedicalGuideModel {
    //实现被@Ignore注解忽略的方法
    public void bingIndex(BaseObserver baseObserver){
        LXHttp.getInstance().createService(BingApi.class)
                .bingIndex()
                .compose(LXHttp.getInstance().applySchedulers(baseObserver));
    }
}
```

|LXModel|说明|
|:---:|:---:|
|value|生成代码的名称（可忽略）|
|networkEngine|用于发起网络请求的类（可忽略）|
|networkService|网络请求接口|

|LXModelImpl|自定义实现网络请求方法|


如果你不满意想自己封装网络库也是可以的 ,有一下两种方式（getInstance()方法必须存在并且是静态方法）推荐使用第一种

```
第一种 先获取到HttpClient 对象在根据自己的需要去进行构建新的请求对象
public class MedicalGuideNetwork {

    private static HttpClient mHttpClient = new HttpClient();

    public static HttpClient getInstance() {
        return mHttpClient
                .newBuilder()
                .setAppHandlerInterface(new MedicalGuideAppHandler())
                .builder();
    }
}


第二种 直接通过HttpClient.Builder()去构建新的请求对象
public class MedicalGuideNetwork{

    public static ServiceInterface getInstance() {
        return HttpClient.Builder()
                .setAppHandlerInterface(new AppHandlerInterface() {
                    @Override
                    public Interceptor[] createInterceptors() {
                        return new Interceptor[]{new MedicalGuideInterceptor()};
                    }

                    @Override
                    public <T> Function<T, T> createAppErrorHandler() {
                        return null;
                    }
                })
                .builder();
    }
}

```
**5、使用LXBind.bind方法进行绑定:**
```
    @Test
    public void bingTest(){
        System.out.println("测试开始: ");
        final CountDownLatch latch = new CountDownLatch(1);
        BingContract.Model mBindModel = LXBind.bind(BingContract.Model.class);
//        MedicalGuideContract.Model model = LXBind.bind(MedicalGuideContract.Model.class);
        //测试MedicalGuideModel实现的方法
        mBindModel.bingIndex(new BaseObserver<String>() {
            @Override
            public void onSuccess(String str) {
                System.out.println("onSuccess: " + str);
                latch.countDown();
            }

            @Override
            public void onFailure(Throwable e) {
                System.out.println("onFailure: " + e.getMessage());
                latch.countDown();
            }
        });

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("测试结束: " + Thread.currentThread());
    }
```
混淆规则
```
#okhttp3.x
-dontwarn com.squareup.okhttp3.**
-keep class com.squareup.okhttp3.** { *;}
-dontwarn okio.**
-dontwarn com.squareup.okhttp.**
-keep class com.squareup.okhttp.{*;}
-dontwarn javax.annotation.**
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase
-dontwarn org.codehaus.mojo.animal_sniffer.*
-dontwarn okhttp3.internal.platform.ConscryptPlatform

#retrofit
-dontwarn retrofit.**
-keep class retrofit.** { *; }
-keepattributes Signature
-keepattributes Exceptions
-dontwarn okio.**

#Rxjava RxAndroid
-dontwarn rx.*
-dontwarn sun.misc.**

#Gson
-keep class com.google.gson.** {*;}
-keep class com.google.**{*;}
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
-keep class com.google.gson.examples.android.model.** { *; }

-keep class cc.turbosnail.**{*;}
#你自己的包名
-keep class cc.turbosnail.lrhnethttp.mvp.contract?_BindModel {*;}
-keepclasseswithmembernames class * {
    @cc.turbosnail.lrhannotation.* <fields>;
}
-keepclasseswithmembernames class * {
    @cc.turbosnail.lrhannotation.* <methods>;
}
```
联系作者：QQ  758648178
