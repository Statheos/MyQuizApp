package com.mastercoding.quizapp.retrofit;

import com.mastercoding.quizapp.model.QuestionsList;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;

public interface QuestionsAPI {

    @GET("questionsapi.php")
    Call<QuestionsList> getQuestions();

}
