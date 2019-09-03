package guy.sawyer.com.entershopper.main

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import guy.sawyer.com.cms.model.City
import guy.sawyer.com.entershopper.ui.main.MainViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainViewModelTest {

    private var viewModel = MainViewModel()
    private lateinit var cities: List<City>

    @Before
    fun setUp() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        viewModel.init(appContext)
        Thread.sleep(2000)
        cities = viewModel.getCities().value!!
    }

    @Test
    fun mainViewModel_GetCities_ShouldReturnCityList() {
        Assert.assertEquals(cities, viewModel.getCities().value)
    }

    @Test
    fun mainViewModel_GivenCityName_ShouldReturnCityObject() {
        val city = cities[0]
        Assert.assertEquals(city, viewModel.getCity("Cape Town"))
    }

    @Test
    fun mainViewModel_GivenCityName_ShouldReturnMallsList() {
        val malls = cities[0].malls
        Assert.assertEquals(malls, viewModel.getMallsInCity("Cape Town"))
    }

    @Test
    fun mainViewModel_GivenMallName_ShouldReturnMallObject() {
        val mall = cities[0].malls[0]
        Assert.assertEquals(mall, viewModel.getMallInCity("Cape Town", "Century City"))
    }

    @Test
    fun mainViewModel_GivenMallName_ShouldReturnShopsList() {
        val shops = cities[0].malls[0].shops
        Assert.assertEquals(shops, viewModel.getShopsInMall("Century City"))
    }

    @Test
    fun mainViewModel_GivenCityName_ShouldReturnShopsList() {
        val shops = cities[0].malls[0].shops.toMutableList()
        shops += cities[0].malls[1].shops
        Assert.assertEquals(shops, viewModel.getShopsInCity("Cape Town"))
    }
}