# Quizle Server

**Quizle Server** is a robust and scalable back-end application built with the **Ktor framework** in **Kotlin**. It's designed to power the Quizle platform, providing all the necessary functionality to manage and deliver quizzes and questions to users.

This project is built following a **Clean Architecture** approach with distinct Data, Domain, and Presentation layers, ensuring a highly maintainable.

-----

### 📝 Key Features


1.  **Quiz and Question Management**: A comprehensive API for all CRUD (Create, Read, Update, Delete) operations on quizzes and their questions.
2.  **Secure Authentication**: Uses JSON Web Tokens (JWT) to protect API routes, ensuring that only authenticated users can manage quiz content.
3.  **MongoDB Integration**: Utilizes MongoDB for persistent and scalable data storage.
4.  **Cloud Storage**: Integrates with AWS S3 for scalable and reliable storage of quiz-related assets like images.
5.  **Clean Architecture**: The project is organized into three distinct layers to separate concerns: Presentation, Domain, and Data.
6.  **Dependency Injection**: The Koin framework is used to manage dependencies, improving code organization and testability.
7.  **Centralized Error Handling**: The Status Pages plugin provides consistent and clean API responses by handling exceptions centrally.
8.  **Comprehensive Logging**: The Call Logging plugin generates detailed logs of all client requests, which is crucial for monitoring and debugging.

-----

## Getting Started

1.  **Clone the repository**:

    ```bash
    git clone https://github.com/MamonAburawi/Quizle-Server.git
    ```

2.  **Configure Environment Variables**:
    Add your configurations for JWT, MONO_DB, and AWS in local variables in your IDLE

    ```properties
    # JWT Configuration
    JWT_SECRET=your_super_secret_key
    JWT_ISSUER=your_app_domain
    JWT_AUDIENCE=your_app_domain
    JWT_REALM=your_realm
    JWT_EXPIREY=your_date_were_token_expire_in

    # AWS S3 Configuration
    AWS_ACCESS_KEY_ID=your_access_key
    AWS_SECRET_ACCESS_KEY=your_secret_key


    # MONGO DB Configuration
    MONGO_DB_URL=your_mongo_db_url
    
    ```

Here is the content of the `secret.conf` file to use for your setup.

```hocon
jwt {
    secret = ${?JWT_SECRET}
    issuer = ${?JWT_ISSUER}
    audience = ${?JWT_AUDIENCE}
    realm = ${?JWT_REALM}
    expiry = ${?JWT_TOKEN_EXPIRY}
}

aws {
     accessKey = ${?AWS_ACCESS_KEY_ID}
     secret = ${?AWS_SECRET_ACCESS_KEY}
}

mongo {
    url = ${?MONGO_DB_URL}
}

```

3.  **Build and Run the Server**:
    Once your dependencies are running, you can start the Ktor server.

    ```bash
    ./gradlew run
    ```




-----

## API Endpoints

 The API is designed to manage quizzes and their questions. All endpoints will soon be secured with a valid JWT in the Authorization header.
## Quiz Question Endpoints

  * **`GET /quiz/questions?topicCode={topicCode}`**
      * Retrieves all quiz questions for a specific topic.
  * **`POST /quiz/questions`**
      * Creates or updates a single quiz question.
  * **`GET /quiz/questions/{id}`**
      * Retrieves a specific quiz question by its unique ID.
  * **`DELETE /quiz/questions/{id}`**
      * Deletes a quiz question using its unique ID.
  * **`POST /quiz/questions/bulk`**
      * Inserts multiple quiz questions in a single request.
  * **`GET /quiz/questions/random?limit={limit}&topicId={topicId}`**
      * Fetches a specified number of random questions from a given topic.
  * **`GET /quiz/topic/active`**
      * Lists the topics that have associated questions.

### JSON Data Structure for Quiz Question

The following JSON object represents the data structure for a single quiz question used across the relevant endpoints:

```json
{
  "id": "string",
  "topic_Id": "string",
  "question_text": "string",
  "correct_answer": "string",
  "master_owner_Id": "string",
  "incorrect_answers": [
    "string"
  ],
  "owners_Ids": [
    "string"
  ],
  "img_url": "string",
  "created_at": 0,
  "updated_at": 0,
  "report_count": 0,
  "level": "string",
  "tags": [
    "string"
  ],
  "explanation": "string"
}
```

The `GET /quiz/questions` and `GET /quiz/questions/random` endpoints will return an array of this object. The `POST /quiz/questions/bulk` endpoint accepts an array of this object in its request body. The other endpoints use a single instance of this object for their request or response body.

-----

## Quiz Topic Endpoints

  * **`GET /quiz/topic`**
      * Retrieves a list of all available quiz topics.
  * **`DELETE /quiz/topic/{id}`**
      * Deletes a quiz topic by its ID.
  * **`POST /quiz/topic`**
      * Creates or updates a quiz topic.
  * **`GET /quiz/topic/{id}`**
      * Retrieves a specific quiz topic by its unique ID.
  * **`GET /quiz/topic/search?titleAr=&titleEn=&subTitleAr=&subTitleEn=&tag={tag}`**
      * Searches for quiz topics based on various criteria like title or tags.

### JSON Data Structure for Quiz Topic

This JSON object represents the data structure for a quiz topic, used by the related endpoints.

```json
{
  "id": "string",
  "title_Ar": "string",
  "title_En": "string",
  "subtitle_Ar": "string",
  "subtitle_En": "string",
  "owners_Ids": [
    "string"
  ],
  "master_owner_Id": "string",
  "topic_color": "string",
  "imgUrl": "string",
  "tags": [
    "string"
  ],
  "views_count": 0,
  "like_count": 0,
  "dislike_count": 0,
  "played_count": 0,
  "quiz_time_min": 0,
  "is_deleted": false,
  "is_public": false,
  "created_at": 0,
  "updated_at": 0
}
```

The `GET` endpoints in this section will return either a single instance or an array of this object. The `POST` endpoint accepts a single instance in its request body.

-----

## Issue Report Endpoints

  * **`GET /issue/report`**
      * Retrieves all submitted issue reports.
  * **`POST /issue/report`**
      * Submits a new issue report.
  * **`DELETE /issue/report/{id}`**
      * Deletes an issue report by its ID.

### JSON Data Structure for Issue Report

This is the JSON structure for an issue report.

```json
{
  "id": "string",
  "question_id": "string",
  "issue_type": "string",
  "additional_comment": "string",
  "user_id": "string",
  "create_at": 0,
  "is_resolved": false
}
```

The `GET` endpoint returns an array of this object, while the `POST` endpoint accepts a single instance in its request body.

-----

## User Endpoints

  * **`GET /user/{id}`**
      * Retrieves a user's details using their unique ID.
  * **`POST /user/register`**
      * Registers a new user account.
  * **`POST /user/login?email={email}&password={password}`**
      * Authenticates a user and provides a login token.
  * **`PATCH /user/update`**
      * Updates a user's profile information.
  * **`POST /user/force_logout?userId={userId}`**
      * Forces a specific user to log out.
  * **`GET /user/activity?userName={userName}`**
      * Retrieves a log of user activities, optionally filtered by username.
  * **`POST /user/add_activity`**
      * Adds a new user activity record to the system.

### JSON Data Structures for User and User Activity

Below are the data structures for user and user activity.

#### User

```json
{
  "id": "string",
  "user_name": "string",
  "email": "string",
  "password": "string",
  "account_type": "string",
  "phone": 0,
  "token": {
    "access_token": "string",
    "exp_at": 0,
    "created_at": 0,
    "type": "string"
  },
  "gender": "string",
  "img_profile": "string",
  "favorite_topics_ids": [
    "string"
  ],
  "liked_topics_ids": [
    "string"
  ],
  "disliked_topics_ids": [
    "string"
  ],
  "result_quizzies_ids": [
    "string"
  ],
  "time_spent_quizzing_in_min": 0,
  "total_correct_answers": 0,
  "total_quizzes": 0,
  "country_code": "string",
  "language": "string",
  "is_active": false,
  "is_public": false,
  "created_at": 0,
  "updated_at": 0,
  "settings": {
    "enable_notification_app": false,
    "enable_quiz_time": false,
    "switch_to_custom_time_in_min": false,
    "custom_quiz_time_in_min": 0
  }
}
```

#### Log Event

```json
{
  "id": "string",
  "user_name": "string",
  "created_at": 0,
  "action": "string",
  "user_id": "string"
}
```

-----

## App Release Endpoints

  * **`GET /release/last`**
      * Retrieves the details of the most recent application release.
  * **`POST /release`**
      * Inserts a new app release record.

### JSON Data Structure for App Release

This is the data structure for an app release.

```json
{
  "id": "string",
  "version_name": "string",
  "version_code": 0,
  "release_date": 0,
  "release_note_Ar": "string",
  "release_note_En": "string",
  "download_link": "string"
}
```

-----

## User Profile Image Endpoints

  * **`POST /user/addImageProfile`**
      * Uploads and sets a new profile image for a user.
  * **`GET /user/imageProfile?imageUrl={imageUrl}`**
      * Retrieves a user's profile image from the specified URL.
  * **`DELETE /user/deleteImageProfile?imageUrl={imageUrl}`**
      * Deletes a user's profile image using its URL.

### JSON Data Structure for User Profile Image

These endpoints do not use a JSON body for the image itself. The `POST` request uses `multipart/form-data`, and the `GET` and `DELETE` endpoints use a query parameter.

## Project Structure (Clean Architecture)

The project is divided into three distinct modules to enforce the dependency rule (**Presentation** → **Domain** → **Data**):

  * **presentation**: This layer contains the Ktor application, including all API endpoints, routes, and `ApplicationCall` handling. It depends only on the Domain Layer.
  * **domain**: This is the core of the application. It holds the business logic and use cases. This layer is independent of any frameworks and defines interfaces for data access (e.g., `QuizRepository` interfaces) but not their implementations.
  * **data**: This layer contains the implementations of the data sources. It is where the concrete logic for interacting with **MongoDB** and **AWS S3** resides, providing the actual data to the Domain Layer via the defined interfaces.

