*IRC Weather Bot
This is an IRC chat bot created as a project for my Computer Science class.

***Libraries Used:

* Uses the PircBot library for IRC communications: http://www.jibble.org/pircbot.php
* Uses the Google Gson library for JSON parsing: https://github.com/google/gson
* Uses the Open Weather Map API: http://openweathermap.org/

***Usage:

By default, it will connect to  Freenode#cs2336jacob using the nickname JacobWeatherBot

It can handle commands in the form of `weather {location}` or `{location} weather`. Location can either be a city name
or a zip code. Caveat: city name can only be a single word. If you want to use "New York" then instead put "NewYork"

It can also has basic sentence recognition. If a message contains the word "weather" and also contains a zip code, it
will return the weather for that zip code.

If a location is unable to be determined, it will default to checking the weather in Richardson.

****Examples:

    weather 10002 #Will respond with weather in New York
    austin weather #Will respond with the weather in Austin
    What is the weather in 75081? #Will respond with the weather in Plano

***Disclaimers:

* This is a simple project created for a computer science class.
* It handles only a few cases, and cannot interpret sentences (i.e. does not use any Natural Language Processing).
* My Open Weather Map API key is exposed in the WeatherService class. I understand that this is bad practice.
