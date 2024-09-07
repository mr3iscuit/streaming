# Audio streaming API

## Project description
The Project is a Spring Boot based RESTful API that offers chunked Audio streaming over HTTPs. This application uses JWT (Json Web Token) with Spring Security. Using Docker Compose to connect Spring Boot to MySql

## Technologies used
- Java 17
- Spring Boot
- JWT
- MySql
- Lombok
- Spring Data JDBC
- SpringDoc OpenAPI

## Api Endpoints
### Authentication Controller
1. **`POST /auth/register`**
    - **Operation**: Register a new user.
    - **Request Body**: `RegisterRequest`
    - **Response**: `AuthenticationResponse`

2. **`POST /auth/refresh-token`**
    - **Operation**: Refresh the JWT token.
    - **Response**: `200 OK`

3. **`POST /auth/authenticate`**
    - **Operation**: Authenticate a user and generate a JWT.
    - **Request Body**: `AuthenticationRequest`
    - **Response**: `AuthenticationResponse`

### Audio Controller

4. **`GET /audio/{id}`**
    - **Operation**: Retrieve audio metadata by ID.
    - **Parameters**:
        - `id` (path parameter)
    - **Response**: `AudioGetDTO`

5. **`PATCH /audio/{id}`**
    - **Operation**: Update audio metadata by ID.
    - **Parameters**:
        - `id` (path parameter)
    - **Request Body**: `AudioPostDTO`
    - **Response**: `AudioEntity`

6. **`GET /audio`**
    - **Operation**: Retrieve a list of all audios.
    - **Response**: List of `AudioGetDTO`

7. **`POST /audio/{audioId}/upload-chunk`**
   - **Operation**: Upload a chunk of audio data.
   - **Parameters**:
      - `audioId` (path parameter)
      - `chunkIndex` (query parameter)
   - **Request Body**: Multipart form-data (file upload)
   - **Response**: `200 OK` (String)
 
8. **`GET /audio/{audioId}/download-chunk`**
    - **Operation**: Download a specific chunk of audio data.
    - **Parameters**:
        - `audioId` (path parameter)
        - `chunkIndex` (query parameter)
    - **Response**: Array of bytes (chunk data)

### User Controller
9. **`PATCH /users`**
    - **Operation**: Change the user's password.
    - **Request Body**: `ChangePasswordRequest`
    - **Response**: `200 OK`

## How to run the Project ?
### Prerequisites:
- JDK 17 or later
- Docker
- Docker Compose

### Steps:
1. Clone the repository
```bash
git clone "https://github.com/mr3iscuit/streaming"
```

2. Build the project
```bash
./gradlew clean build
docker compose start
```

How to stop ?
```bash
docker compose stop
```