package cz.utb.fai.nativetranslator;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.squareup.moshi.Moshi;

import cz.utb.fai.nativetranslator.databinding.FragmentFirstBinding;
import cz.utb.fai.nativetranslator.pojo.ApiResponse;
import cz.utb.fai.nativetranslator.pojo.ResponseData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class FirstFragment extends Fragment {

    public static final String BASE_URL = "https://api.mymemory.translated.net/";
    private FragmentFirstBinding binding;
    private MyApiEndpointInterface apiService;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        Moshi moshi = new Moshi.Builder().build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build();

        apiService = retrofit.create(MyApiEndpointInterface.class);

        binding = FragmentFirstBinding.inflate(inflater, container, false);

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.progressBar.setVisibility(View.GONE);

        binding.btnTranslate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);*/
                //binding.txtOutput.setText(binding.txtInput.getText());
                binding.progressBar.setVisibility(View.VISIBLE);
                getTranslation();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void getTranslation(){
        String q = binding.txtInput.getText().toString();
        String langpair = "en|cs";

        Call<ApiResponse> call = apiService.getResponse(q, langpair);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                int statusCode = response.code();
                ApiResponse data = response.body();
                //ResponseData responseData = data.responseData;

                Log.v("MYAPP", data.responseData.translatedText);
                binding.txtOutput.setText(data.responseData.translatedText);
                binding.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                // Log error here since request failed
            }
        });
    }


}