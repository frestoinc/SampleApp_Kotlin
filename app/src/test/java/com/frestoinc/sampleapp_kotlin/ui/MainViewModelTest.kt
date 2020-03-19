package com.frestoinc.sampleapp_kotlin.ui

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.frestoinc.sampleapp_kotlin.api.data.manager.DataManager
import com.frestoinc.sampleapp_kotlin.api.data.remote.toState
import com.frestoinc.sampleapp_kotlin.api.resourcehandler.Resource
import com.frestoinc.sampleapp_kotlin.di.appModule
import com.frestoinc.sampleapp_kotlin.di.dataModule
import com.frestoinc.sampleapp_kotlin.di.networkModule
import com.frestoinc.sampleapp_kotlin.di.roomModule
import com.frestoinc.sampleapp_kotlin.utils.getData
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.mockito.Mockito.verify

/**
 * Created by frestoinc on 02,March,2020 for SampleApp_Kotlin.
 */
@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class MainViewModelTest : KoinTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = MainCoroutineTestRule()

    @MockK
    private lateinit var context: Application

    @RelaxedMockK
    private lateinit var dataManager: DataManager

    @RelaxedMockK
    private lateinit var mainViewModel: MainViewModel

    private val data = getData(this)

    private val result1 = Resource.success(data)

    @Before
    fun setUp() {
        stopKoin()
        context = mockk()
        startKoin {
            androidContext(context)
            modules(
                listOf(
                    appModule,
                    networkModule,
                    roomModule,
                    dataModule
                )
            )
        }
        dataManager = mockk()
        mainViewModel = mockk()
    }

    @After
    fun tearDown() {
        unmockkAll()
        stopKoin()
    }

    @Test
    fun testValidRepository() = coroutineTestRule.testDispatcher.runBlockingTest {
        coEvery { dataManager.getRemoteRepository() } returns result1

        mainViewModel.getStateLiveData().observeForever {}
        mainViewModel.getRemoteRepo()

        verify(dataManager).getRemoteRepository()

        assertTrue(mainViewModel.getStateLiveData().hasObservers())
        assertEquals(mainViewModel.getStateLiveData().value, result1.toState())
    }
}