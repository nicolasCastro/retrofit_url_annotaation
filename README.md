# Retrofit - Multiples base urls with annotations


## The problem
When we create a new application, we will probably have multiple API calls.
And probably some of them will be at different URLs.
Retrofit provides a Url annotation to use in our methods,
but we don't want to manage the string and we don't want to pass it on every invocation of a method.
We want each service to know which url it should point to.
Well, in this tutorial we are going to see a solution.

## ServiceFactory
You may be using a factory to create service instances.
Something like this:

```
class ServiceFactory() {
    private val defaultApiUrl = "https://www.example.com/v1/"

    fun <T> createInstance(clazz: Class<T>): T {
        return retrofit(defaultApiUrl).create(clazz)
    }
    
    private fun retrofit(apiUrl: String) = Retrofit.Builder()
        .baseUrl(apiUrl)
        .client(okHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    
    private fun okHttpClient(): OkHttpClient = OkHttpClient.Builder().build()
}
```

But... how can we change the base url dynamically?

Well, to do that we need to change the `ServiceFactory` class to take the value of `defaultApiUrl` depending on the service we are looking to create.

To achieve that we will create a new annotation class called `ApiUrl`.

```
@Target(AnnotationTarget.CLASS)
annotation class ApiUrl(
    val url: String
)
```

We will apply this class to retrofit interfaces as class annotation.

## Make the change

Now is the time to see the change in the `ServiceFactory` class.
Let's start looking if our service contains the annotation we just created.
In the `createInstance` method, we will add the following line to the beginning:

```
val apiUrlAnnotation = clazz.annotations.find { it is ApiUrl } as ApiUrl?
```

If it does, let's use the value for the new instance. Otherwise we will use the default defined in `ServiceFactory`.

How do we use it in a service? Easy, just add the annotation to the class indicating the value of the url.

```
@ApiUrl(url = "https://www.somthingelse.com/v1/")
interface VideoService {
   // more code
}
```

And that's it! Now we can change the base url of Retrofit without having to differentiate or create two different paths.
Let's see a complete example.

## Example

```
class ServiceFactory() {
    private val defaultApiUrl = "https://www.example.com/v1/"

    fun <T> createInstance(clazz: Class<T>): T {
        // Check if the service have an annotation
        val apiUrlAnnotation = clazz.annotations.find { it is ApiUrl } as ApiUrl?
        // Take the url value, in another hand  use the default
        val url = apiUrlAnnotation?.url ?: defaultApiUrl
        // And finally create the service using de extracted url
        return retrofit(url).create(clazz)
    }
    
    private fun retrofit(apiUrl: String) = Retrofit.Builder()
        .baseUrl(apiUrl)
        .client(okHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    
    private fun okHttpClient(): OkHttpClient = OkHttpClient.Builder().build()
}
```

## An extra improvement

Instead of using urls in your code, compile them into the BuildConfig class.
In this way you can save your urls in the gradle.properties file and refer to them in the build.config file.

**gradle.properties**
```
base_url="https://www.example.com/v1/"
other_url="https://www.somthingelse.com/v1/"
```

**build.gradle**
```
buildConfigField('String', 'API_URL', "$base_url")
buildConfigField('String', 'OTHER_URL', "$other_url")
```

and finally use it like that:
```
// In a service class
@ApiUrl(url = BuildConfig.OTHER_URL)
// In the service faactory class
private val defaultApiUrl = BuildConfig.API_URL
```

Check the complete code in the [github repo](https://github.com/nicolasCastro/retrofit_url_annotaation).
