# EnterShopper

CMS (CityMallShopper) Module Usage

Initialization

	- Instantiate cmsRepo = CmsRepository()
	- Call cmsRepo.connectAndGetApiData(context)
	- Use live property cmsRepo.isResponseStale to determine if result was from network or cache

The CMS module has a custom OkHttpClient which handles network issues.
Cache Rules:

	- Has Network : Network data will be returned, and cached
	- Service call requested within 5 seconds of last : Cache data returned
	- No Network : Cache data returned
	- Cache data older than 7 days : Cache discarded and error returned


Further Usage

	- cmsRepo.provideCities() : Will return a List<City> Object
	
	- cmsRepo.provideCityByName(cityName: String) : Will return a City object
	
	- cmsRepo.provideMallsByCity(cityName: String) : Will return a List<Mall> object
	
	- cmsRepo.provideMallInCity(cityName: String, mallName: String) : Will return a Mall object
	
	- cmsRepo.provideShopsInMall(mallName: String) : Will return a List<Shop> object
	
	- cmsRepo.provideSingleShopInMall(mallName: String, shopName: String) : Will return a Shop object
	
	- cmsRepo.provideShopsInCity(cityName: String) : Will return a List<Shop> object
