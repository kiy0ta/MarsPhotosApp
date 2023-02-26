package com.example.android.marsphotos.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com"

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

/**
 * Retrofit には、ウェブサービスのベース URI と、
 * ウェブサービス API をビルドするためのコンバータ ファクトリが必要です。
 * コンバータは、ウェブサービスから返されたデータをどのように処理するかをRetrofit に伝えます。
 * この演習では、Retrofit はウェブサービスから JSON レスポンスを取得し、
 * String として返す必要があります。
 * Retrofit には、文字列およびその他のプリミティブ型をサポートする ScalarsConverter があるため、
 * ScalarsConverterFactory のインスタンスを使ってビルダーで addConverterFactory() を呼び出します。
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

/**
 * アプリは Retrofit API サービスのインスタンスを 1 つだけ必要とします。
 * したがって、オブジェクト宣言を使用して、アプリの他の部分にサービスを公開します。
 * シングルトン パターンを使用すると、オブジェクトのインスタンスが 1 つだけ作成され、
 * そのオブジェクトへのグローバル アクセス ポイントが 1 つであることが保証されます。
 * オブジェクト宣言の初期化はスレッドセーフで、最初のアクセスの際に行われます。
 */
object MarsApi {
    /**
     * MarsApi オブジェクト宣言内に、遅延初期化された
     * MarsApiService 型の Retrofit オブジェクト プロパティを
     * retrofitService という名前で追加します。
     * この遅延初期化により、プロパティが最初に使用されるときに初期化されるようにします。
     */
    val retrofitService: MarsApiService by lazy {
        retrofit.create(MarsApiService::class.java)
    }
}

interface MarsApiService {
    /**
     * getPhotos() メソッドが呼び出されると、
     * Retrofit はリクエストの開始に使用するベース URL（Retrofit ビルダーで定義した URL）の末尾に
     * エンドポイント photos を追加します。
     */
    @GET("photos")
    //  suspend 関数にすることで、コルーチン内からこのメソッドを呼び出すことが可能になります
    suspend fun getPhotos(): List<MarsPhoto>
}



