package byrd.riley.notificationservicekotlin.model

interface Texter {
    fun sendText(receiver: String, messageBody: String)
    fun sendText(receiver: String, sender: String, messageBody: String)
}