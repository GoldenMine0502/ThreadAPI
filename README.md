# ThreadAPI
The Library counting consecutive time 



# how to use
extend your own class or create your annoymous class

you should provide fps for APIThread

APIThread(int fps)

or

APIThread(TimeUnitFactory.unit, value)

  ex) APIThread(TimeUnitFactory.MS, 100) = APIThread(0.1)
  
  ex) APIThread(TimeUnitFactory.MINUTE, 2)
  



# Example code

APIThread thread = new APIThread(TimeUnitFactory.SECOND, 1) {

  int time;


  /*when one tick is passed*/
  
  @Override
  
  public void onThreadExecute() throws InterruptedException {
    
    System.out.println(++time + " Sec");
  
  }

  /*if your code on onThreadExecute() is too very heavy to calculate time normally, 
  
  The ThreadAPI create new time calculation and refresh.        
  
  then, this method is called.*/
  
  @Override
  
  public void onKeepUp() {

  }

  /*when you call APIPause or etc, 
  
  The ThreadAPI wakes up Thread.sleep(). 
  
  then, this method is called*/ 
 
 
  @Override
  
  public void onInterrupt() {

  }

  /*when you start thread, this method is called*/
  
  @Override
  
  public void onStart() {

  }

  /*when you call APIPause(), this method is called*/
  
  @Override
  
  public void onPause() {

  }

  /*when you call APIResume(), this methoid is called*/
  
  @Override
  
  public void onResume() {

  }

  /*when you call APIStop(), this method is called*/
  
  @Override
  
  public void onStop() {

  }
  
};

// start thread

thread.start();



APIMultiThread is unstable. I suggest you don't use.


Good Luck!

by GoldenMine (Korean)

blog.naver.com/ehe123
