package indigo

class Deck {
    val cards: MutableList<Card> = ArrayList(totalAmountOfCards)
    val playerCards: MutableList<Card> = ArrayList(6)
    val cpuCards: MutableList<Card> = ArrayList(6)
    val deckCards: MutableList<Card> = ArrayList()


    init {
        reset()
    }

    fun reset() {
        cards.clear()
        suits.forEach { suit -> ranks.forEach { rank ->
            val card = Card(rank = rank, suit = suit)
            cards.add(card)
        } }
        shuffle()
    }
    fun resetCards() {
        for (i in 0..5) {
            playerCards.add(cards.first())
            cards.removeFirst()
        }
        for (i in 1..6) {
            cpuCards.add(cards.first())
            cards.removeFirst()
        }
    }

    fun shuffle() = cards.shuffle()

    fun getCards(amount: Int): List<Card> {
        //works like try catch
        require(amount in 1..totalAmountOfCards) {
            "Invalid number of cards."
        }
        require(amount <= cards.size) {
            "The remaining cards are insufficient to meet the request."
        }
        return List(amount) { cards.removeLast() }
    }
    fun getPlayerCards() {
        print("Cards in hand: ")
        for (i in playerCards.indices) {
            print("${i + 1})${playerCards[i]} " )
        }
        println()
    }
}