package indigo

import kotlin.system.exitProcess

class Game {
    private val deck = Deck()
    private var topCard: Card? = null
    private var yorn: Boolean? = null
    private var scorePlayer = 0
    private var scoreCPU = 0
    private var playerWinsCard: MutableList<Card> = ArrayList()
    private var cpuWinsCard: MutableList<Card> = ArrayList()
    private var last: Boolean? = null

    init {
        println("Indigo Card Game")
        var choise = ask("Play first?")
        while (choise != "yes" && choise != "no") {
            choise = ask("Play first?")
        }
        yorn = choise == "yes"
        start()
        info()
    }

    private fun reset() {
        deck.reset()
        deck.resetCards()
    }

    private fun start() {
        reset()
        deck.shuffle()
        val cards = deck.getCards(4)
        cards.forEach {e -> deck.deckCards += e}
        topCard = cards.last()
        print("Initial cards on the table: ")
        cards.print()

    }
    private fun info() {
        while(true) {
            println()
            if (deck.deckCards.size == 0) {
                println("No cards on the table")
                topCard = null
            } else {
                println("${deck.deckCards.size} cards on the table, and the top card is $topCard")
            }
            checker()
            yorn = when (yorn) {
                true -> {
                    deck.getPlayerCards()
                    playerPlay()
                    false
                }
                false -> {
                    cpuPlay()
                    true
                }
                else -> false
            }
        }
    }
    private fun checker() {
        if (deck.playerCards.size == 0 && deck.cpuCards.size == 0) {
            if(deck.cards.size == 0) {
                if (last == null) {
                    if (yorn == true) {
                        addScoresAndCards("Player")
                    } else{
                        addScoresAndCards("Computer")
                    }
                } else {
                    if (last == true) {
                        addScoresAndCards("Player")
                    } else{
                        addScoresAndCards("Computer")
                    }
                }
                when {
                    playerWinsCard.size == cpuWinsCard.size -> {
                        if (yorn == true) {
                            scorePlayer += 3
                        } else {
                            scoreCPU += 3
                        }
                    }
                    playerWinsCard.size > cpuWinsCard.size -> scorePlayer += 3
                    else -> scoreCPU += 3
                }
                printScoreAndCards()
                println("Game Over")
                exitProcess(0)
            }
            deck.resetCards()
        }
    }

    private fun playerPlay() {
        try {
            val num:Int = ask("Choose a card to play (1-${deck.playerCards.size}):").toInt() - 1
            deck.deckCards.add(deck.playerCards[num])
            checkToWin("Player", deck.playerCards[num])
            topCard = deck.playerCards[num]
            deck.playerCards.removeAt(num)
        } catch (e: java.lang.NumberFormatException) {
            playerPlay()
        } catch (e: java.lang.IllegalArgumentException) {
            playerPlay()
        } catch (e: java.lang.IndexOutOfBoundsException) {
            playerPlay()
        }
    }
    private fun cpuPlay() {
        println(deck.cpuCards.joinToString(" "))
        val countOfCandidate = countCandidatesOfCPU()
        val index: Int
        if (deck.cpuCards.size == 1) {
            index = 0
        } else if (countOfCandidate == 1) {
            index = findFirstCandidate()
        }
        else if (deck.deckCards.size == 0 || countOfCandidate == 0) {
            val a = theSameSuit()
            val b = theSameRank()
            index = if (a > -1) {
                a
            } else if (b > -1) {
                b
            } else {
                0
            }
        } else {
            val a = findBySuit()
            val b = findByRank()
            index = if (a > -1) {
                a
            } else if (b > -1) {
                b
            } else {
                0
            }

        }
        println("Computer plays ${deck.cpuCards[index]}")
        deck.deckCards.add(deck.cpuCards[index])
        checkToWin("Computer", deck.cpuCards[index])
        topCard = deck.cpuCards[index]
        deck.cpuCards.removeAt(index)
    }
    private fun findBySuit(): Int {
        for (i in deck.cpuCards.indices) {
            if (deck.cpuCards[i].suit == topCard?.suit) {
                return i
            }
        }
        return -1
    }
    private fun findByRank(): Int {
        for (i in deck.cpuCards.indices) {
            if (deck.cpuCards[i].rank == topCard?.rank) {
                return i
            }
        }
        return -1
    }
    private fun theSameSuit(): Int {
        for (i in deck.cpuCards.indices) {
            for (j in deck.cpuCards.indices) {
                if (i != j && deck.cpuCards[i].suit == deck.cpuCards[j].suit) {
                    return i
                }
            }
        }
        return -1
    }
    private fun theSameRank() : Int {
        for (i in deck.cpuCards.indices) {
            for (j in deck.cpuCards.indices) {
                if (i != j && deck.cpuCards[i].rank == deck.cpuCards[j].rank) {
                    return i
                }
            }
        }
        return -1
    }
    private fun findFirstCandidate(): Int {
        for (i in deck.cpuCards.indices) {
            if (deck.cpuCards[i].rank == topCard?.rank || deck.cpuCards[i].suit == topCard?.suit) {
                return i
            }
        }
        return 0
    }
    private fun countCandidatesOfCPU(): Int {
        var cnt = 0
        for (i in deck.cpuCards) {
            if (i.suit == topCard?.suit || i.rank == topCard?.rank) cnt++
        }

        return cnt
    }
    private fun checkToWin(name: String, card: Card) {
        if (card.theSame(topCard)) {
            addScoresAndCards(name)
            println("$name wins cards")
            printScoreAndCards()
        }
    }
    private fun printScoreAndCards() {
        println("Score: Player $scorePlayer - Computer $scoreCPU")
        println("Cards: Player ${playerWinsCard.size} - Computer ${cpuWinsCard.size}")
    }
    private fun addScoresAndCards(name: String) {
        if (name == "Player") {
            last = true
            deck.deckCards.forEach{e -> if (e.good()) scorePlayer++}
            playerWinsCard += deck.deckCards
        } else {
            last = false
            deck.deckCards.forEach{e -> if (e.good()) scoreCPU++}
            cpuWinsCard += deck.deckCards
        }
        deck.deckCards.clear()
    }
}