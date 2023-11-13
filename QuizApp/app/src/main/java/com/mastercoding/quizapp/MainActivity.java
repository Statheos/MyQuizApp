package com.mastercoding.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.mastercoding.quizapp.databinding.ActivityMainBinding;
import com.mastercoding.quizapp.model.Question;
import com.mastercoding.quizapp.model.QuestionsList;
import com.mastercoding.quizapp.viewmodel.QuizViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    QuizViewModel quizViewModel;
    List<Question> questionsList;

    static int result =0 ;
    static int totalQuestions = 0;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        questionsList = new ArrayList<>();



        binding = DataBindingUtil.setContentView(this,
                R.layout.activity_main);

        // Resetting the Scores:
        result = 0;
        totalQuestions = 0;

        // getting the response:
        quizViewModel = new ViewModelProvider(this)
                .get(QuizViewModel.class);

        // Displaying the First Question
        quizViewModel.getQuestionsListLiveData().observe(this, new Observer<QuestionsList>() {
            @Override
            public void onChanged(QuestionsList questions) {
                questionsList  = questions;

                Log.i("TAGY","The first Question: "+questions.get(0));

                binding.txtQuestion.setText("Question 1 :  " +questions.get(0).getQuestion());
                binding.radio1.setText(questions.get(0).getOption1());
                binding.radio2.setText(questions.get(0).getOption2());
                binding.radio3.setText(questions.get(0).getOption3());
                binding.radio4.setText(questions.get(0).getOption4());

            }
        });


        // Displaying the Next Questions

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Direct the user to the results Activity
                if (binding.btnNext.getText().equals("Finish")){
                    Intent intent = new Intent(MainActivity.this, ResultsActivity.class);
                    startActivity(intent);
                    finish();
                }





                Log.i("TAGY", "sizey" + questionsList.size());

                int selectedOption = binding.radioGroup.getCheckedRadioButtonId();

                if (selectedOption != -1) {
                    RadioButton radioButton = findViewById(selectedOption);

                    // More questions to Display??
                    if ( (questionsList.size() - i) > 0){

                    // getting the number of questions
                    totalQuestions = questionsList.size();

                    // check if the chosen option is correct
                    if (radioButton.getText().toString().equals(questionsList.get(i).getCorrectOption())) {
                        result++;
                        binding.txtResult.setText("Correct Answer: " + result);
                    }


                    if(i == 0){
                        i++;
                    }



                    // Displaying the next Questions
                    binding.txtQuestion.setText("Question " + (i+1) + " :  " + questionsList.get(i).getQuestion());

                    binding.radio1.setText(questionsList.get(i).getOption1());
                    binding.radio2.setText(questionsList.get(i).getOption2());
                    binding.radio3.setText(questionsList.get(i).getOption3());
                    binding.radio4.setText(questionsList.get(i).getOption4());


                    // Checking if it is the last question
                    if (i == (questionsList.size() - 1)) {
                        binding.btnNext.setText("Finish");
                    }

                    binding.radioGroup.clearCheck();
                    i++;


                    }else{
                        if (radioButton.getText().toString().equals(questionsList.get(i-1).getCorrectOption())){
                            result++;
                            binding.txtResult.setText("Correct Answer :" +result);

                        }else{

                        }








                    }


                }else{
                    Toast.makeText(MainActivity.this, "You need to make a selection", Toast.LENGTH_SHORT).show();
                }




                }

        });




    }
}