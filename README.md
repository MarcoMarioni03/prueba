# Prueba - Marco Marioni

### Local Setup

To start the application locally, run the following command in the project root directory:

```bash
docker-compose up --build
```


### Authentication and API Usage Instructions


To authenticate and get the Bearer token, you need to send a `POST` request to Firebase Authentication's `signInWithPassword` API endpoint with the following details:

**Endpoint:**

https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=AIzaSyBTctB0zhEko2AUgbJBD2Y-nuY_IAo7Za0

**Request Body:**
```json
{
    "email": "marco03_1997@hotmail.com",
    "password": "demoPassword",
    "returnSecureToken": true
}
```

**Example using CURL:**

```bash
curl -X POST \
  'https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=AIzaSyBTctB0zhEko2AUgbJBD2Y-nuY_IAo7Za0' \
  -H 'Content-Type: application/json' \
  -d '{
    "email": "marco03_1997@hotmail.com",
    "password": "demoPassword",
    "returnSecureToken": true
  }'
```

**Response:**

You will receive a JSON response containing the `idToken` (the Bearer token), which is used for authentication in subsequent requests to your API.

The `idToken` returned in the response should be used as the Bearer token for authentication in your API requests.

**Need Access?**

If you want access to the Firebase project, send me an email at [marco03_1997@hotmail.com](mailto:marco03_1997@hotmail.com). I will invite you to the project, and you can create your own user with email and password.

### Using the Bearer Token in Swagger UI

Once you have obtained your Bearer token from Firebase Authentication, follow these steps to use it in Swagger UI:

1. Swagger UI: `http://localhost:8080/swagger-ui/index.html`

2.  In the top right corner of the Swagger UI interface, you will see an "Authorize" button. Click it to open the authorization dialog.

3.  In the pop-up dialog, you will see a field labeled **"Value"**. Enter the Bearer token you obtained from Firebase Authentication into this field, without prefix.

