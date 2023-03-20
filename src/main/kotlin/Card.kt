package indigo

val ranks = arrayOf("A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K")
val suits = arrayOf("♦", "♥", "♠", "♣")
val totalAmountOfCards = ranks.size * suits.size
data class Card(val rank: String, val suit: String, val goods: String = "A10JQK") {
    override fun toString() = "$rank$suit"
    fun theSame(other: Card?): Boolean {
        return other?.rank == this.rank || other?.suit == this.suit
    }
    fun good(): Boolean = this.rank in goods
}