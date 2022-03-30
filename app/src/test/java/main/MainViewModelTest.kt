package main

import androidx.lifecycle.Observer
import com.treelineinteractive.recruitmenttask.data.repository.ShopRepository
import com.treelineinteractive.recruitmenttask.ui.main.MainViewModel
import com.treelineinteractive.recruitmenttask.ui.main.MainViewState
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.fest.assertions.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    private val shopRepository: ShopRepository = mockk(relaxed = true)
    private val observer = mockk<Observer<MainViewState>>()
    private val slot = slot<MainViewState>()
    private val list = arrayListOf<MainViewState>()

    private lateinit var sut: MainViewModel

    @BeforeEach
    fun setup() {
        createViewModel()
    }

    @Test
    fun `should init viewState`() {
        // Arrange
        createViewModel()
        // Act
        // Assert
        assertThat(sut.stateLiveData.value).isEqualsToByComparingFields(
            MainViewState(
                isLoading = false,
                error = null,
                items = arrayListOf()
            )
        )
    }

    private fun createViewModel() {
        sut = MainViewModel(
            shopRepository = shopRepository
        )
        sut.products.value = arrayListOf()
        sut.stateLiveData.observeForever(observer)

        every { observer.onChanged(capture(slot))} answers {
            list.add(slot.captured)
        }
    }
}