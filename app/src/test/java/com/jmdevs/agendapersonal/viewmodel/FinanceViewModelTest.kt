package com.jmdevs.agendapersonal.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jmdevs.agendapersonal.data.local.entity.Transaction
import com.jmdevs.agendapersonal.data.repository.FinancialRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class FinanceViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var financialRepository: FinancialRepository
    private lateinit var financeViewModel: FinanceViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        financialRepository = mockk(relaxed = true)
        // El ViewModel se crea aquí. Su bloque `init` lanza las corrutinas de carga inicial.
        financeViewModel = FinanceViewModel(financialRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test balance is calculated correctly`() = runTest {
        // GIVEN
        val transactions = listOf(
            Transaction(id = 1, amount = 150000L, type = "INGRESO", category = "Salario", description = "Pago mensual", date = System.currentTimeMillis(), paymentMethod = "Transferencia", currency = "CLP", balance = 150000L),
            Transaction(id = 2, amount = 25000L, type = "GASTO", category = "Comida", description = "Supermercado", date = System.currentTimeMillis(), paymentMethod = "Tarjeta", currency = "CLP", balance = 125000L)
        )
        val expectedBalance = 125000.0
        coEvery { financialRepository.getAllTransactions() } returns flowOf(transactions)

        // WHEN
        // Forzamos la ejecución de las corrutinas pendientes (la del `init` del ViewModel).
        testDispatcher.scheduler.advanceUntilIdle()
        // Ahora que la corrutina ha terminado, el `StateFlow` tiene el valor correcto.
        val actualBalance = financeViewModel.balance.value

        // THEN
        assertEquals(expectedBalance, actualBalance, 0.0)
    }

    @Test
    fun `test all transactions are loaded correctly`() = runTest {
        // GIVEN
        val expectedTransactions = listOf(
            Transaction(id = 1, amount = 50000L, type = "INGRESO", category = "Venta", description = "Venta online", date = System.currentTimeMillis(), paymentMethod = "Efectivo", currency = "CLP", balance = 50000L),
            Transaction(id = 2, amount = 10000L, type = "GASTO", category = "Transporte", description = "Gasolina", date = System.currentTimeMillis(), paymentMethod = "Tarjeta", currency = "CLP", balance = 40000L)
        )
        coEvery { financialRepository.getAllTransactions() } returns flowOf(expectedTransactions)

        // WHEN
        // Forzamos la ejecución de las corrutinas pendientes (la del `init` del ViewModel).
        testDispatcher.scheduler.advanceUntilIdle()
        // Ahora que la corrutina ha terminado, el `StateFlow` tiene el valor correcto.
        val actualTransactions = financeViewModel.allTransactions.value

        // THEN
        assertEquals(expectedTransactions, actualTransactions)
    }
}
