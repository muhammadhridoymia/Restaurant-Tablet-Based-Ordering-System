

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf

data class CartItem(
    val food: Food,
    var quantity: Int
)

class CartViewModel : ViewModel() {

    private val _cartItems = mutableStateListOf<CartItem>()
    val cartItems: List<CartItem> = _cartItems

    fun addToCart(food: Food, quantity: Int) {

        val existingItem = _cartItems.find { it.food.id == food.id }

        if (existingItem != null) {
            existingItem.quantity += quantity
        } else {
            _cartItems.add(CartItem(food, quantity))
        }
    }

    fun removeFromCart(foodId: String) {
        _cartItems.removeAll { it.food.id == foodId }
    }

    fun increaseQuantity(foodId: String) {
        _cartItems.find { it.food.id == foodId }?.let {
            it.quantity++
        }
    }

    fun decreaseQuantity(foodId: String) {
        _cartItems.find { it.food.id == foodId }?.let {
            if (it.quantity > 1) {
                it.quantity--
            }
        }
    }

    fun getTotalItems(): Int {
        return _cartItems.sumOf { it.quantity }
    }

    fun getTotalPrice(): Int {
        return _cartItems.sumOf { it.food.price * it.quantity }
    }
}