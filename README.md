# home-manager-users
Microservice that stores users of the Home Manager platform.

## Endpoints

| Method | URL                           |
|--------|-------------------------------|
| POST   | /users                        |
| GET    | /users/{id}                   |

Data are provided to the client with the following format:

```json
{
  "data": "Device not found",
  "error": "" // Only in development environment
}
```

### Users

#### Get all registered Users

Return all registered users.

Method: ```GET```

URL: ```/users```

##### Success response

Returns a list of Users.

Code: ```200```

Response:

```json
{
  "data": [
    {
      "_id": "5dc947ccdfb5d10013713a8f",
      "name": "Test User",
      "email": "user@test.com"
    }
  ]
}
```

#### Register a new User

Register a new User to the system.

Method: ```POST```

URL: ```/users```

Body constraints:

```json
{
  "name": "string", // required
  "email": "string", // optional
}
```

##### Success response

Returns the new User object.

Code: ```200```

Example body:

```json
{
  "name": "Test User",
  "email": "user@test.com"
}
```

Response:

```json
{
  "data": {
    "_id": "5dc947ccdfb5d10013713a8f",
    "name": "Test User",
    "email": "user@test.com"
  }
}
```

##### Errors

- Code: ```400```: Missing data.
- Code: ```400```: The user is already exists.

#### Get an User by ID

Return the requested User.

Method: ```GET```

URL: ```/users/{id}```

##### Success response

Code: ```200```

Example: ```/users/5dc947ccdfb5d10013713a8f```

Response:

```json
{
  "data": {
    "_id": "5dc947ccdfb5d10013713a8f",
    "name": "Test User",
    "email": "user@test.com"
  }
}
```

##### Errors

- Code: ```400```: Invalid id

- Code: ```404```: User not found