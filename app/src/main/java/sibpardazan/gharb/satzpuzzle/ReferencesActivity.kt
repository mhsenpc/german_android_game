package sibpardazan.gharb.satzpuzzle

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import sibpardazan.gharb.satzpuzzle.databinding.ActivityReferencesBinding

class ReferencesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReferencesBinding
    private lateinit var adapter: ReferenceUrlAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReferencesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupClickListeners()
    }

    private fun setupRecyclerView() {
        val urls = resources.getStringArray(R.array.reference_urls).toList()
        adapter = ReferenceUrlAdapter(urls)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@ReferencesActivity)
            adapter = this@ReferencesActivity.adapter
        }
    }

    private fun setupClickListeners() {
        binding.backButton.setOnClickListener {
            finish()
        }
    }
}