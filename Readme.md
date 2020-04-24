<p align="center">
	<img src="https://github.com/Abhishekds94/Movie-Mania/blob/master/app/src/main/res/drawable/logo.png" width="180">
</p>

# Movie Mania

Movie Mania is an Android application that displays all the movies from [The Movie DataBase](https://www.themoviedb.org/?language=en-US) based on Popular movies, Top rated and favorite movies based on user selection.

This application was developed as a part of [Udacity's Android Nanodegree course](https://www.udacity.com/course/android-developer-nanodegree-by-google--nd801).


## Installation

Clone this repository using,

```
git clone https://github.com/Abhishekds94/Movie-Mania.git
```

## Usage
API keys required,

1. Create an API key for your project on [The Movie DataBase](https://www.themoviedb.org/settings/api) API console
2. Now, replace it with API-KEY in ```DashboardActivity.java``` file in the project and run the application

## Features

1. The app lists all the movies that are available on TMDB website and can be sorted as Top Rated or Popular movies
2. On clicking the movie poster, the details of the movie can be seen such as Synopsis, Release Date, Rating, Plot of the movie, Trailers and the Reviews
3. On clicking the heart-shaped button in the movie details page, the movie can be set as a favorite and can be seen under the favorite sorting list when sorted
4. All the trailers will be opened on [YouTube](www.youtube.com). This is handled using intents


## Building

You can build the app with Android Studio or with `./gradlew assembleDebug` command.

## Screenshots
<div>
  <img src="https://github.com/Abhishekds94/Movie-Mania/blob/master/Screenshots/img1.jpg" width="220">

  <img src="https://github.com/Abhishekds94/Movie-Mania/blob/master/Screenshots/img2.jpg" width="220">

  <img src="https://github.com/Abhishekds94/Movie-Mania/blob/master/Screenshots/img3.jpg" width="220">
</div>

<div>
  <img src="https://github.com/Abhishekds94/Movie-Mania/blob/master/Screenshots/img4.jpg" width="220">

  <img src="https://github.com/Abhishekds94/Movie-Mania/blob/master/Screenshots/img5.jpg" width="220">

  <img src="https://github.com/Abhishekds94/Movie-Mania/blob/master/Screenshots/img6.jpg" width="220">
</div>

<div>
  <img src="https://github.com/Abhishekds94/Movie-Mania/blob/master/Screenshots/img7.jpg" width="220">

  <img src="https://github.com/Abhishekds94/Movie-Mania/blob/master/Screenshots/img8.jpg" width="220">

  <img src="https://github.com/Abhishekds94/Movie-Mania/blob/master/Screenshots/img9.jpg" width="220">
</div>

## App Working Video
<a href="https://youtu.be/wQ2zRsGT2UI?" target="_blank"><img src="https://github.com/Abhishekds94/Movie-Mania/blob/master/Screenshots/video.jpg" 
alt="Video Working" width="220" /></a>

## Future Scope
Below are a few of the ideas that I could think of to enhance the application,
* Adding offline usage
* Adding share feature for all the movies individually

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.