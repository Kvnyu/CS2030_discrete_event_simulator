class PagerPreack extends Pager{

    PagerPreack(String name){
        super(name);
    }

    PagerPreack(String name, String transmitterName){
        super(name, transmitterName);
    }

    Host ack(){
        return new Transmitter(this.getName(), this.getTransmitterName(), true);
    }
}
