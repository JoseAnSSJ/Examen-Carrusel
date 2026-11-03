-keepattributes Signature, *Annotation*, InnerClasses, EnclosingMethod
-keep class com.example.carrucel.data.remote.dto.** { *; }

-keep,allowobfuscation,allowshrinking interface retrofit2.Call
-keep,allowobfuscation,allowshrinking class retrofit2.Response
-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation