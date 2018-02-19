package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class EventController implements Initializable {

   @FXML
   private Button btnSingleChooseTmp;//单个文件导入模板选择按钮

   @FXML
   private TextField tfdSingleTmp;//单个文件导入模板路径框
   
   @FXML
   private Button btnMultiChooseTmp;//文件目录导入模板选择按钮

   @FXML
   private TextField tfdMultiTmp;//文件目录导入模板路径框
   
   @FXML
   private MenuBar menuBar;//菜单栏
   
   String[] downloadableExtensions = {".doc", ".xls", ".zip", ".exe", ".rar", ".pdf", ".jar", ".png", ".jpg", ".gif"};
    
   @Override
   public void initialize(URL location, ResourceBundle resources) {
    }
 
   /**
    * 弹出单个文件导入模板选择框
    */
   public void showSingleTemplateChooseDialog(){
       FileChooser fileChooser = new FileChooser();
       FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
       fileChooser.getExtensionFilters().add(extFilter);
       File file = fileChooser.showOpenDialog(btnSingleChooseTmp.getContextMenu());
       if (null != file) {
    	   tfdSingleTmp.setText(file.getAbsolutePath());
       }
   }
   
   /**
    * 弹出多个文件导入模板选择框
    */
   public void showMultiTemplateChooseDialog(){
       FileChooser fileChooser = new FileChooser();
       FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
       fileChooser.getExtensionFilters().add(extFilter);
       File file = fileChooser.showOpenDialog(btnMultiChooseTmp.getContextMenu());
       if (null != file) {
    	   tfdMultiTmp.setText(file.getAbsolutePath());
       }
   }
   
   /**
    * 弹出构建导入模板页面
    */
   public void toBuildTmp(){
		try {
 			Stage window = new Stage();
			window.setTitle("构建导入模板");
			AnchorPane root = FXMLLoader.load(getClass()
			        .getResource("/ui/buildTmp.fxml"));
			Scene scene = new Scene(root);
			WebView buildTmpWv = (WebView)scene.lookup("#buildTmpWv");
			
			final WebEngine webEngine = buildTmpWv.getEngine();
			webEngine.load("http://rj.baidu.com/index.html?fxq");
			webEngine.locationProperty().addListener(new ChangeListener<String>() {
	            @Override
	            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
 	            	
 	                for(String downloadAble : downloadableExtensions) {
	                    if ( newValue.endsWith(downloadAble)) {
	                    	 try {
// 	                             if(!file.exists()) {
//	                                 file.mkdir();
//	                             }
	                            
 	                             File fileTmp = new File(newValue);
 	                     	     FileChooser fileChooser = new FileChooser();
	    	            	     fileChooser.setInitialFileName(fileTmp.getName());
	                             File download = fileChooser.showSaveDialog(null);
//	                             File download = new File(file + "/" + fileTmp.getName());
	                             if (null == download) {
	                            	 break;
	                             }
	                             if(download.exists()) {
 	                                 showDialog("提示","下载的文件已存在");
	                                 break;
	                             }
  	                             FileUtils.copyURLToFile(new URL(webEngine.getLocation()), download);
  	                             showDialog("提示","下载完成,保存路径: " + download.getAbsolutePath());
   	                             break;
	                    	 } catch(Exception e) {
	                             e.printStackTrace();
	                         }
	                    }
	                }
	            }
	        });
			
			window.setScene(scene);
			//使用showAndWait()先处理这个窗口，而如果不处理，main中的那个窗口不能响应
			window.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
 
	}
   
   public void toAbout(){
	   System.out.println("关于页面");
   }
   
   public Stage showDialog(String title,String message) throws IOException{
	   Stage window = new Stage();
		window.setTitle(title);
		AnchorPane root = new AnchorPane();
		Label label = new Label();
		label.setText(message);
		root.getChildren().add(label);
		Scene scene = new Scene(root);
		window.setScene(scene);
		//使用showAndWait()先处理这个窗口，而如果不处理，main中的那个窗口不能响应
		window.showAndWait();
		return window;
   }
   
	 
}