package desktop.excel.dataDrive;

import desktop.exception.TafRuntimeException;

public class DataDriveException extends TafRuntimeException {
  public DataDriveException(){
    super();
  }

  public DataDriveException(String message){
    super(message);
  }

  public DataDriveException(Throwable t){
    super(t);
  }

  public DataDriveException(String message, Throwable t){
    super(message, t);
  }
}
