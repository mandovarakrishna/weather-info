# Weather Service - WEB SERVICE

This web service provides the capailities to retrieve current and forecasted weather information. 

### Details

#### current-weather GET

Local: 
	
	http://localhost:8080/api/current-weather?city={city}&zipCode={zipCode}
	

#### forecast-weather GET

Local: 

	http://localhost:8080/api/forecast-weather?city={city}&zipCode={zipCode}
	

### Open Source API

#### openWeatherMap API

URLs: 

	OPEN_WEATHER_MAP_CURRENT=https://api.openweathermap.org/data/2.5/weather?q={city}&zip={zip}&appid={appid}
	OPEN_WEATHER_MAP_FORECAST=https://api.openweathermap.org/data/2.5/forecast?q={city}&zip={zip}&appid={appid}


#### weatherBit API

URLs:

	WEATHER_BIT_CURRENT=https://api.weatherbit.io/v2.0/current?postal_code={zip}&city={city}&key={appKey}
	WEATHER_BIT_FORECAST=https://api.weatherbit.io/v2.0/forecast/daily?postal_code={zip}&city={city}&key={appKey}
