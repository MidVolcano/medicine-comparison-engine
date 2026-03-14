# **AI Clinical Comparison Engine**

Mini full stack application that takes in an iput containing conflicting redical records from healthcare systems and using ChatGPT's API, compares them and provides actual clinical reasoning.

This project was made as part of the interview process for a Full Stack Developer - EHR Integration Intern. The system takes in multiple medical records and finds which is the best option, assigns a confidence score, and produces "clinical reasoning for reconciliation decisions" that "Detect implausible data (e.g., impossible vital signs, drug-disease mismatches)".

## **Tech Used** 

### **Front End**
- HTML
- JavaScript
- Chart.js as a visual pie chart

### **Back End**
- Java
- Spring Boot
- REST
- OPENAI's API

## **How It Looks Before & After**
### **https://sites.google.com/arizona.edu/medical-reconciliation/home?authuser=8**

## **Optimizations**
- The source code is body readable and easily understood since most of the java code should be understood by anyone proficient in java.
- Handles API limits by stopping the program early if it exceeds current funds in my OPENAI account and if there is an API error, it is printed to console.
- A PIE chart to better map the confidence score

## **Setup Instructions**
Prerequisites:
- Java (I used 25 for this)
- Maven for Spring Boot
- Your own OPENAI API KEY, I used eclipse for this project so I set it in environment, also the secret phrase in the controller is set to "mateom-secret" so when you run it you can use that or change it.
- Boomerang to run the POST commands, with the headers Name: x-api-key and Value: mateom-secret
- The endpoints for POST are: /api/reconcile/medication and /api/validate/data-quality
- The endpoints to reach and view the website are: /dashboard.html

### **Running the Project in Eclipse**

1. **Download the repository**

Download the ZIP from GitHub and extract it.

---

2. **Open Eclipse**

Start Eclipse and make sure you have the following installed:

- Maven 

---

3. **Import the Project**

In Eclipse (Correct on my MacBook):

1. Click **File**
2. Click **Import**
3. Select **Maven**
4. Choose **Existing Maven Projects**
5. Click **Next**

---

4. **Select the Project Folder**

Click **Browse** and navigate to the project folder you downloaded.

Eclipse should do the rest automatically

---

5. **Set OpenAI API Key**

Before running the application you must set your OpenAI API key as an environment

In Eclipse:

1. Right click the project
2. Click **Run Configurations**
3. Select the Spring Boot application
4. Go to the **Environment** tab
5. Add a new variable:

```
Name: OPENAI_API_KEY
Value: your_openai_api_key
```

---

6. **Run the Project**

Open the **ClinicalDataApplication** file and right click it
Choose to run as java application

The server will then run on your browser specifically at:

```
http://localhost:8080
```

---

7. **Open the Website**

Once the application is running, open your browser go  to:

```
http://localhost:8080/dashboard.html
```
---

8. **Testing the API**

To test API endpoints manually you can use an app called Boomerang to do the
POST requests

Include the header:

```
Name: x-api-key
Value: mateom-secret
```

Endpoints available:

```
POST /api/reconcile/medication
POST /api/validate/data-quality
```
## **Why OPENAI?**

I originaly chose OPENAI because that was the A.I tool which I knew the most about. Prior to this I had no experience inorporating an AI into my code, so when I went on youtube to find a guide, a youtuber I had previously been a subscribed to and liked his teaching style had a SPRING boot guide for ChatGPT available. 
https://www.youtube.com/watch?v=ufx0dGArkt0
If anyone else wishes to learn, this video basically taught me everything I needed to know in order to complete this project.

## **Key Decisions / Trade Offs**

One of the main design decisions for this project was choosing the backend language and framework. I chose Java with Spring Boot because Java is the language I have the most experience with, and I wanted to further develop my skills working with Spring Boot and REST in specific.

While building this project, I recognized that using **Python** would have probably made my life easier especially for the API portion but I personally prefer Java, and gained more from writing this in java (Specifically all the debugging) than had I done this in python.

## **Improvements With Time**

As of now for this project, the most annoying part for me was doing the webpage. I am decent at HTML and JavaScript but as my skills improve, I would like to come back to this project and really update the Front-End ui. I would also wish to add in more error handeling and a database since this was stored locally.

## Architecture Decisions (Brief)

### Backend Framework: Java + Spring Boot
I chose **Java with Spring Boot** because it is widely used in industry specifically backend systems. Spring Boot makes it straightforward to build **REST APIs**, and was relatively easy to learn.

### REST API Design
The application exposes two endpoints:
- `POST /api/reconcile/medication`
- `POST /api/validate/data-quality`

### Deterministic Reconciliation Logic
The medication reconciliation decision is made using a **scoring system** based on:
- source reliability
- recency of the record
I allocated 10 total points and of that 6 of them were allocated to the reliability, where High : 6, Medium : 4, Low : 2, None: 0. The recency was also made up the years since admission so 2026 : 4, 2025 : 3, 2024 : 2, and anything older as 0. 

This ensures the system produces predictable results and also helped avoid completely relying on the AI's decision for reasoning.

### AI Integration for Reasoning
The **OpenAI API** is used only create a string containing the reasoning behind the outcome my data produced, with there being infinite inputs, having the AI generate an easy to read response was both a smart and no brainer decision.


### Frontend Dashboard
A simple **HTML + JavaScript** front end was chosen 

The Website:
- displays the reconciled medication
- shows AI generated reasoning
- maps the confidence scores using Chart.js to a pie chart 

---

## Test Data / Example Requests

### Medication Reconciliation Example
(They all have to be JSONS)

Request:

```json
{
  "sources": [
    {
      "system": "Hospital EHR",
      "medication": "Metformin 1000mg twice daily",
      "lastUpdated": "2024-10-15",
      "sourceReliability": "high"
    },
    {
      "system": "Primary Care",
      "medication": "Metformin 500mg twice daily",
      "lastUpdated": "2026-01-20",
      "sourceReliability": "high"
    },
    {
      "system": "Pharmacy",
      "medication": "Metformin 1000mg daily",
      "lastUpdated": "2025-01-25",
      "sourceReliability": "medium"
    }
  ],
  "patientContext": {
    "conditions": ["Type 2 Diabetes"],
    "recentLabs": {
      "eGFR": 45
    }
  }
}
```

Example Response:

```json
{
  "reconciledMedication": "Metformin 500mg twice daily",
  "confidenceScore": 0.9,
  "reasoning": "Primary care record is the most recent clinical encounter. Dose reduction may be appropriate given declining kidney function (eGFR 45). Pharmacy record may reflect an older prescription.",
  "recommendedActions": [
    "Update Hospital EHR to Metformin 500mg twice daily",
    "Verify with pharmacist that the correct dose is being filled"
  ],
  "clinicalSafetyCheck": "PASSED"
}
```

---

### Data Quality Validation Example

Request:

```json
{
  "demographics": {
    "name": "John Doe",
    "dob": "1955-03-15",
    "gender": "M"
  },
  "medications": ["Metformin 500mg", "Lisinopril 10mg"],
  "allergies": [],
  "conditions": ["Type 2 Diabetes"],
  "vital_signs": {
    "blood_pressure": "340/180",
    "heart_rate": 72
  },
  "last_updated": "2024-06-15"
}
```

Example Response:

```json
{
  "overall_score": 62,
  "breakdown": {
    "completeness": 60,
    "accuracy": 50,
    "timeliness": 70,
    "clinical_plausibility": 40
  },
  "issues_detected": [
    {
      "field": "allergies",
      "issue": "No allergies documented - likely incomplete",
      "severity": "medium"
    },
    {
      "field": "vital_signs.blood_pressure",
      "issue": "Blood pressure 340/180 is physiologically implausible",
      "severity": "high"
    }
  ]
}
```

Five more test cases are included in the download aswell. (At src/test/java)



