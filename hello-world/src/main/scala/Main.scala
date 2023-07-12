import zio._

object Main extends ZIOAppDefault {
//  override def run = for {
//    _ <- Console.printLine("run!")
//  } yield ()

  abstract class Notification
  case class AppPush(title: String, message: String) extends Notification

  case class Email(sourceEmail: String, title: String, body: String) extends Notification

  case class SMS(sourceNumber: String, message: String) extends Notification

  case class VoiceRecording(contactName: String, link: String) extends Notification
  case class Board(title: String, txt: String, notifications: List[Notification])

  override val run = for {
    _ <- ZIO.unit
    b = Board(
      title = "투자하기",
      txt = "대표 상품",
      notifications = List(
        AppPush(title = "App", message = "앱푸시가 도착했습니다."),
        Email(sourceEmail = "email", title = "email-title", body = "email-body"),
        SMS(sourceNumber = "sourceNumber", message = "message"),
        SMS(sourceNumber = "김사장", message = "message"),
        VoiceRecording(contactName = "contactName", link = "link"),
      )
    )
    _ = b.notifications.foreach { noti =>
      val matchedMsg = noti match {
        case AppPush(title, message) => s"애부시: ${title}, ${message}"
        case Email(sourceEmail, title, body) => s"이메일: ${sourceEmail}, ${title}, ${body}"
        case SMS(sourceNumber, message) if sourceNumber == "김사장" => "차단"
        case SMS(sourceNumber, message) => s"문자: ${sourceNumber}, ${message}"
        case VoiceRecording(contactName, link) => s"녹음: ${contactName}, ${link}"
        case _ => "몰라요"
      }
      println(matchedMsg)
    }
  } yield ()
}
