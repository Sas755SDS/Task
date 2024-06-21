import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.InputStreamReader

fun parseSpecifications(jsonString: String): List<Specification> {
    val gson = Gson()
    val type = object : TypeToken<List<Specification>>() {}.type
    Log.d("TAG", "parseSpecifications: ")
    return gson.fromJson(jsonString, type)

}

data class ItemTax(val value: Int) // Simple data class for item taxes

data class SpecificationItem(
    val id: String,
    val name: List<String>,
    val price: Int,
    val sequenceNumber: Int,
    val is_default_selected: Boolean,
    val specificationGroupId: String,
    val uniqueId: Int
)

data class Specification(
    val id: String,
    val name: List<String>,
    val sequenceNumber: Int,
    val uniqueId: Int,
    val list: List<SpecificationItem>,
    val maxRange: Int,
    val range: Int,
    val type: Int,
    val isRequired: Boolean,
    val modifierId: String,
    val modifierGroupId: String,
    val modifierGroupName: String,
    val modifierName: String,
    val isAssociated: Boolean,
    val userCanAddSpecificationQuantity: Boolean
)

data class Product(
    val id: String,
    val name: List<String>,
    val price: Int,
    val itemTaxes: List<ItemTax>,
    val specifications: List<Specification>
)
 fun getJsonStringFromAssets(context: Context): String {
    val asset = context.assets.open("data.json")
    val br = BufferedReader(InputStreamReader(asset))
    val sb = StringBuilder()
    var line: String?
    while (br.readLine().also { line = it } != null) {
        sb.append(line)
    }
    br.close()
     Log.d("TAG", "getJsonStringFromAssets: ${sb.toString()}")
    return sb.toString()
}