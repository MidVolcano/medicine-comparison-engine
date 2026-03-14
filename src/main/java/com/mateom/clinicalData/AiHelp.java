package com.mateom.clinicalData;

import org.springframework.stereotype.Service;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;

@Service
public class AiHelp {

	private final OpenAIClient client;

	public AiHelp() {
		client = OpenAIOkHttpClient.fromEnv();
	}

	public String generateReasoning(String medication, String condition, String labName, Integer labValue) {

		try {

			String prompt = "You are a clinical decision support assistant.\n\n" + "Patient condition: " + condition
					+ "\n" + "Medication: " + medication + "\n" + "Lab value: " + labName + " = " + labValue + "\n\n"
					+ "Explain briefly why this medication choice is reasonable.";

			ResponseCreateParams params = ResponseCreateParams.builder().model("gpt-4.1-mini").input(prompt).build();

			Response response = client.responses().create(params);

			String raw = response.toString();

			int start = raw.indexOf("text=") + 5;
			int end = raw.indexOf(", type=output_text");

			if (start > 4 && end > start) {
				return raw.substring(start, end);
			}

			return "AI reasoning doesnt work";

		} catch (Exception e) {
			return "AI reasoning unavailable due to API error";
		}
	}
}