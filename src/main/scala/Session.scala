import akka.actor.ActorRef

/**
  * Created by 1 on 2/13/17.
  */
class Session(remoteController: ActorRef) {
    while(true){
        remoteController ! onShow
        Thread.sleep(500)
    }
}
