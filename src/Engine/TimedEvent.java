package Engine;

class TimedEvent extends Thread{
	int time;
	TimedEvent(int miliseconds){
		super();
		this.time=miliseconds;
	}

	@Override
	public void run(){
		super.run();
		try{
			Thread.sleep(time);
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}
}
