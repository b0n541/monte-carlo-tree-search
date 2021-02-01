package net.b0n541.ai.game.connect4

class Connect4Board(
    initialState: Array<Array<String>> = EMPTY_STATE_HUMAN_READABLE,
    val nextPlayer: Connect4PlayerSymbol = Connect4PlayerSymbol.O
) {
    companion object {
        private val EMPTY_STATE_HUMAN_READABLE = arrayOf(
            arrayOf(" ", " ", " ", " ", " ", " ", " "),
            arrayOf(" ", " ", " ", " ", " ", " ", " "),
            arrayOf(" ", " ", " ", " ", " ", " ", " "),
            arrayOf(" ", " ", " ", " ", " ", " ", " "),
            arrayOf(" ", " ", " ", " ", " ", " ", " "),
            arrayOf(" ", " ", " ", " ", " ", " ", " ")
        )

        private val EMPTY_STATE = listOf(
            Connect4Column(),
            Connect4Column(),
            Connect4Column(),
            Connect4Column(),
            Connect4Column(),
            Connect4Column(),
            Connect4Column()
        )

        const val MAX_COLUMN_HEIGHT = 6
        const val MAX_ROW_LENGTH = 7
        val VALID_PIECES = setOf("O", "X")

        private fun checkColumnIndex(column: Int) {
            require(column in 0 until MAX_ROW_LENGTH) { "Column must be in range 0..6" }
        }

        private fun checkRowIndex(row: Int) {
            require(row in 0 until MAX_COLUMN_HEIGHT) { "Row must be in range 0..5" }
        }
    }

    private var state: List<Connect4Column>
    private val moves = mutableListOf<Connect4Move>()

    init {
        require(initialState.size == MAX_COLUMN_HEIGHT) { "Initial state must haven 6 rows" }
        for (row in 0 until MAX_COLUMN_HEIGHT) {
            require(initialState[row].size == MAX_ROW_LENGTH) { "Initial state must haven 7 columns" }
        }

        state = if (initialState == EMPTY_STATE_HUMAN_READABLE) {
            EMPTY_STATE
        } else {
            convertToState(initialState)
        }
    }

    val lastMove
        get() = moves.last()

    val isBoardFull
        get() = state.filter { it.isFull }.size == MAX_ROW_LENGTH

    private fun convertToState(initialState: Array<Array<String>>): List<Connect4Column> {
        val columnList = mutableListOf<Connect4Column>()
        for (column in 0 until MAX_ROW_LENGTH) {
            val pieces = mutableListOf<String>()
            for (row in 5 downTo 0) {
                pieces.add(initialState[row][column])
            }
            columnList.add(Connect4Column(pieces))
        }
        return columnList.toList()
    }

    fun getRow(row: Int): List<String> {
        checkRowIndex(row)

        val result = mutableListOf<String>()
        for (column in 0 until MAX_ROW_LENGTH) {
            result.add(getCell(row, column))
        }

        return result.toList()
    }

    fun getColumn(column: Int): List<String> {
        checkColumnIndex(column)

        return state[column].toList()
    }

    fun getCell(row: Int, column: Int): String {
        checkRowIndex(row)
        checkColumnIndex(column)

        return state[column][row]
    }

    fun dropPiece(move: Connect4Move): Connect4Board {
        require(move.player == nextPlayer) { "Next player must be $nextPlayer" }
        checkColumnIndex(move.column)
        require(!state[move.column].isFull) { "Column ${move.column} is full." }

        val newState = mutableListOf<Connect4Column>()
        for (columnIndex in 0 until MAX_ROW_LENGTH) {
            val columnCopy = state[columnIndex].copy()
            if (columnIndex == move.column) {
                columnCopy.add(nextPlayer.toString())
            }
            newState.add(columnCopy)
        }

        val newNextPlayer = when (nextPlayer) {
            Connect4PlayerSymbol.O -> Connect4PlayerSymbol.X
            Connect4PlayerSymbol.X -> Connect4PlayerSymbol.O
        }

        val newBoard = Connect4Board(nextPlayer = newNextPlayer)
        newBoard.state = newState
        newBoard.moves.add(move)

        return newBoard
    }

    fun copy() {

    }

    val gameResult: Connect4GameResult
        get() {
            return when (getWinningPlayer()) {
                "O" -> Connect4GameResult.O_WIN
                "X" -> Connect4GameResult.X_WIN
                else -> if (isBoardFull) Connect4GameResult.DRAW else Connect4GameResult.UNDECIDED
            }
        }

    private fun getWinningPlayer(): String {
        val board = state.map { it.toList() }

        val verticalWin = getVerticalWin(board)
        if (verticalWin != " ") {
            return verticalWin
        }

        val horizontalWin = getHorizontalWin(board)
        if (horizontalWin != " ") {
            return horizontalWin
        }

        val diagnoalWin = getDiagonalWin(board)
        if (diagnoalWin != " ") {
            return diagnoalWin
        }

        return " "
    }

    private fun getVerticalWin(board: List<List<String>>): String {
        for (column in 0 until MAX_ROW_LENGTH) {
            for (row in 0 until MAX_COLUMN_HEIGHT - 3) {
                val playerSymbols = listOf(
                    board[column][row],
                    board[column][row + 1],
                    board[column][row + 2],
                    board[column][row + 3]
                )

                if (playerSymbols.first() != " " && playerSymbols.all { it == playerSymbols.first() }) {
                    return playerSymbols.first()
                }
            }
        }
        return " "
    }

    private fun getHorizontalWin(board: List<List<String>>): String {
        for (row in 0 until MAX_COLUMN_HEIGHT) {
            for (column in 0 until MAX_ROW_LENGTH - 3) {
                val playerSymbols = listOf(
                    board[column][row],
                    board[column + 1][row],
                    board[column + 2][row],
                    board[column + 3][row]
                )

                if (playerSymbols.first() != " " && playerSymbols.all { it == playerSymbols.first() }) {
                    return playerSymbols.first()
                }
            }
        }
        return " "
    }

    private fun getDiagonalWin(board: List<List<String>>): String {
        for (row in 0 until MAX_COLUMN_HEIGHT - 3) {
            for (column in 0 until MAX_ROW_LENGTH - 3) {
                val playerSymbols = listOf(
                    board[column][row],
                    board[column + 1][row + 1],
                    board[column + 2][row + 2],
                    board[column + 3][row + 3]
                )

                playerSymbols.apply {
                    if (first() != " " && all { it == first() }) {
                        return first()
                    }
                }

                val playerSymbols2 = listOf(
                    board[column][row + 3],
                    board[column + 1][row + 2],
                    board[column + 2][row + 1],
                    board[column + 3][row]
                )

                playerSymbols2.apply {
                    if (first() != " " && all { it == first() }) {
                        return first()
                    }
                }
            }
        }

        return " "
    }

    fun getPossibleMoves(): List<Int> {
        val result = mutableListOf<Int>()

        for (index in 0 until MAX_ROW_LENGTH) {
            if (!state[index].isFull) {
                result.add(index)
            }
        }

        return result.toList()
    }

    override fun toString(): String {
        val builder = StringBuilder()
        builder.append("\n\n")
        for (row in MAX_COLUMN_HEIGHT - 1 downTo 0) {
            formatRow(builder, getRow(row))
        }
        builder.append("=====================\n")
        builder.append(" 0  1  2  3  4  5  6  --> $gameResult\n")

        return builder.toString()
    }

    private fun formatRow(builder: StringBuilder, row: List<String>) {
        for (rowEntry in row) {
            builder.append("[$rowEntry]")
        }
        builder.append('\n')
    }

    class Connect4Column(initialPieces: List<String> = listOf()) {
        private val pieces = mutableListOf<String>()

        init {
            require(initialPieces.size <= MAX_COLUMN_HEIGHT) { "Column must be between 0 and 6 pieces tall." }
            val pieces = initialPieces.toMutableList()
            trimColumn(pieces)
            pieces.forEach {
                checkPiece(it)
                add(it)
            }
        }

        private fun trimColumn(pieces: MutableList<String>) {
            if (pieces.isNotEmpty()) {
                pieces.apply {
                    while (isNotEmpty() && last() == " ") {
                        removeLast()
                    }
                }
            }
        }

        val isFull
            get() = pieces.size == MAX_COLUMN_HEIGHT

        fun add(piece: String) {
            checkPiece(piece)
            pieces.add(piece)
        }

        private fun checkPiece(piece: String) {
            require(piece in VALID_PIECES) { "Invalid piece $piece." }
        }

        operator fun get(row: Int): String = getRowValue(row)

        private fun getRowValue(row: Int): String {
            checkRowIndex(row)

            return if (pieces.size > row) {
                pieces[row]
            } else {
                " "
            }
        }

        fun toList(): List<String> {
            val result = pieces.toMutableList()
            result.addAll(Array(MAX_COLUMN_HEIGHT - result.size) { " " }.toList())
            return result
        }

        fun copy(): Connect4Column {
            return Connect4Column(pieces.toList())
        }
    }
}