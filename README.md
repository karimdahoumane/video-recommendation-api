# Video Recommendation API

This is a mini system for recommending videos via a REST API. It includes endpoints for various functionalities.

> **_NOTE:_** This project uses mnvw. There is no need to install Maven.

## Stack technique

![Java](https://img.shields.io/badge/Java%2017-%23ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/Spring%205.3.24-%236DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot%203.2.2-%6DB33F?style=for-the-badge&logo=SpringBoot&logoColor=white)  
![JUnit](https://img.shields.io/badge/JUnit-AE2922?style=for-the-badge&logo=junit5&logoColor=25A162)  
![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)

## Installation

Clone the project

```bash
git clone https://github.com/karimdahoumane/video-recommendation-api.git
```

Build the app

```bash
./mvnw clean install
# or
mvnw.cmd clean install
```

## Run the application locally

---

```bash
./mvnw spring-boot:run
```


## API Reference
#### URL `http://localhost:8080/api/video`

#### Get all videos

```http
  GET /
```

#### Get video by id

```http
  GET /{id}
```

| Path variable | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string (uuid)` | **Required**. Id of video to fetch |

#### Get videos by title

```http
  GET /search/{title}
```

| Path variable | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `title` | `string` | **Required**. Title of video to be searched (min 3 characters) |

#### Create video

```http
  POST /
```

#### Get all movies

```http
  GET /movies
```

#### Get all shows

```http
  GET /shows
```

#### Get similar videos

```http
  GET /similar?id={id}&minCommonLabel={minCommonLabels}
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `string (uuid)` | **Required**. Id of video to fetch |
| `minCommonLabel` | `int` | **Required**. Minimum number of labels that wanted in common.  |

## Running tests

- #### Terminal
```bash
./mvnw test
```
- #### End to end
     
The video type (Video, Movie or Show) is created according to the body of the input request, it can be as follows:

Video :

  ```yaml
      {
        "title": "Prison Break",
        "labels": [
          "Action",
          "Crime",
          "Thriller"
        ]
      }
  ```


Movie :

  ```yaml
      {
        "title": "Get Out",
        "type": "movie",
        "director": "Jordan Peele",
        "release_date": "2017-02-24T12:00:00Z",
        "labels": [
          "Horror",
          "Mystery",
          "Thriller"
        ]
      }
  ```

        
Show :

  ```yaml
      {
        "title": "Friends",
        "type": "show",
        "number_of_episodes": 236,
        "labels": [
          "Comedy",
          "Romance",
          "Sitcom"
        ]
      }
  ```
---

## Data Persistence

- Data is stored in an in-memory database.
