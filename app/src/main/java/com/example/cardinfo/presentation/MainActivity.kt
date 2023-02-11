package com.example.cardinfo.presentation

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.cardinfo.R
import com.example.cardinfo.databinding.ActivityMainBinding
import com.example.cardinfo.di.ApplicationComponent
import com.example.cardinfo.domain.entity.CardInfo
import com.example.cardinfo.presentation.adapters.CardInfoAdapter
import com.google.android.material.snackbar.Snackbar
import java.util.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var binding: ActivityMainBinding
    private lateinit var cardInfoAdapter: CardInfoAdapter

    private val component: ApplicationComponent by lazy {
        (application as CardInfoApplication).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        prepareStateObservation()
        preparePreviousRequestsRecyclerView()
        prepareSearchEditText()
    }

    private fun prepareStateObservation() {
        viewModel.state.observe(this) {
            when (it) {
                is State.Success -> {
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
                    binding.editTextBin.setText("")
                    binding.progressBar.visibility = View.GONE
                    setDataToFields(it.data)
                }
                is State.Progress -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is State.CardNotFound -> {
                    showNotFoundErrorSnackBar()
                    binding.progressBar.visibility = View.GONE
                }
                is State.ServerError -> {
                    showServerErrorSnackBar()
                    binding.progressBar.visibility = View.GONE

                }
                is State.NetworkError -> {
                    showNetworkErrorSnackBar()
                    binding.progressBar.visibility = View.GONE

                }
                is State.InputError -> {
                    showInputErrorSnackBar()
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }

    private fun prepareSearchEditText() {
        binding.editTextBin.setOnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) {
                val imm = getSystemService(InputMethodManager::class.java)
                imm?.hideSoftInputFromWindow(view.windowToken, 0)

            }
        }
        binding.editTextBin.setOnEditorActionListener { view, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.loadCardInfo(view.text.toString().trim())
                binding.editTextBin.clearFocus()
                return@setOnEditorActionListener true
            }
            false
        }
    }

    private fun showServerErrorSnackBar() {
        val snackBar = Snackbar.make(
            binding.root,
            getString(R.string.server_error_ocurred),
            Snackbar.LENGTH_SHORT
        )
        snackBar.show()
    }

    private fun showNetworkErrorSnackBar() {
        val snackBar = Snackbar.make(
            binding.root,
            getString(R.string.check_network_connection),
            Snackbar.LENGTH_SHORT
        )
        snackBar.show()
    }

    private fun showNotFoundErrorSnackBar() {
        val snackBar = Snackbar.make(
            binding.root,
            getString(R.string.card_info_not_found),
            Snackbar.LENGTH_SHORT
        )
        snackBar.show()
    }

    private fun showInputErrorSnackBar() {
        val snackBar = Snackbar.make(
            binding.root,
            getString(R.string.enter_min_6_digits),
            Snackbar.LENGTH_SHORT
        )
        snackBar.show()
    }

    private fun preparePreviousRequestsRecyclerView() {
        cardInfoAdapter = CardInfoAdapter()
        cardInfoAdapter.onItemClickListener = {
            setDataToFields(it)
        }
        with(binding.recyclerViewPreviousRequests) {
            adapter = cardInfoAdapter
            val callback = object :
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val item = cardInfoAdapter.currentList[viewHolder.adapterPosition]
                    viewModel.removeFromPreviousRequests(item.bin)
                }
            }
            val itemTouchHelper = ItemTouchHelper(callback)
            itemTouchHelper.attachToRecyclerView(this)
        }
        viewModel.previousRequests.observe(this) {
            cardInfoAdapter.submitList(it)
        }
    }

    private fun setDataToFields(cardInfo: CardInfo) {
        with(binding) {
            with(cardInfo) {
                textViewCardBin.text = bin
                textViewScheme.text = scheme.capitalize().ifBlank { getString(R.string.unknown) }
                textViewBrand.text = brand.capitalize().ifBlank { getString(R.string.unknown) }
                textViewType.text = type.capitalize().ifBlank { getString(R.string.unknown) }
                textViewPrepaid.text =
                    if (isPrepaid) getString(R.string.yes) else getString(R.string.no)
                textViewNumberLength.text = if (numberLength == -1) {
                    getString(R.string.unknown)
                } else numberLength.toString()

                textViewIsLuhn.text =
                    if (isLuhn) getString(R.string.yes) else getString(R.string.no)
                textViewCountry.text = countryInfo.name.ifBlank { getString(R.string.unknown) }
                textViewCoordinates.text = if (countryInfo.latitude != 0.0) {
                    String.format(
                        Locale.getDefault(),
                        getString(R.string.coordinates_template),
                        countryInfo.latitude,
                        countryInfo.longitude
                    )
                } else getString(R.string.unknown)
                textViewBankName.text = bankInfo.name.ifBlank { getString(R.string.unknown) }
                textViewBankSite.text = bankInfo.url.ifBlank { getString(R.string.unknown) }
                textViewBankPhone.text = bankInfo.phone.ifBlank { getString(R.string.unknown) }

                textViewBankSite.setOnClickListener {
                    val link = cardInfo.bankInfo.url
                    if (link.isNotBlank()) {
                        openLink(link)
                    }
                }
                textViewBankPhone.setOnClickListener {
                    val phone = cardInfo.bankInfo.phone
                    if (phone.isNotBlank()) {
                        openDialer(phone)
                    }
                }
                textViewCoordinates.setOnClickListener {
                    val latitude = cardInfo.countryInfo.latitude
                    val longitude = cardInfo.countryInfo.longitude
                    if (latitude != 0.0 && longitude != 0.0) {
                        openMaps(latitude, longitude)
                    }
                }
            }
        }
    }

    private fun openLink(link: String) {
        Intent(Intent.ACTION_VIEW, Uri.parse("https://$link")).apply {
            startActivity(this)
        }
    }

    private fun openDialer(phone: String) {
        Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phone")
            startActivity(this)
        }
    }

    private fun openMaps(latitude: Double, longitude: Double) {
        val uri = String.format(Locale.ENGLISH, "geo:%f,%f", latitude, longitude)
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        startActivity(intent)
    }

    private fun String.capitalize(): String {
        return this.replaceFirstChar { it.uppercaseChar() }
    }

}