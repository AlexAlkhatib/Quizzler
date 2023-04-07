package com.mniprince.quiz;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/*
Cette classe est une interface pour définir les requêtes HTTP à envoyer à une API REST pour récupérer des données JSON. Elle utilise Retrofit, une bibliothèque Java pour simplifier l'appel des API REST.

La méthode getJson() utilise l'annotation @GET pour spécifier la méthode HTTP (dans ce cas, GET) et l'URL de base de l'API. Elle prend également trois paramètres de requête : categories, limit et difficulty. Ces paramètres sont définis avec l'annotation @Query.

La méthode renvoie un objet Call qui représente une demande asynchrone pour récupérer une liste d'objets QuizModel à partir de l'API.
 */

interface RequestInteface {
    @GET("api/questions?")
    Call<List<QuizModel>> getJson(@Query("categories") String key,
                                  @Query("limit") String key1,
                                  @Query("difficulty") String key2);
}
