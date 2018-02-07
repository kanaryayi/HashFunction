import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileCheck {
	private static String pathOfRegistryFile = "registry.txt";
	private static String pathOfCurrentFile = "file";

	public static void main(String[] args) {
		ReadFile read = new ReadFile(pathOfRegistryFile);
		FileCheck fileCheck = new FileCheck();
		//get list of original data paths from registry file and call getFileContent func. 
		fileCheck.getFileContent(fileCheck.getPathsFromRegistry(read),read);
	}

	private void getFileContent(ArrayList<String> read,ReadFile readFile) {
		//first get paths from given path argument and check if there is new file
		ArrayList<String> filePaths = new ArrayList<>();
		getListofFilesInADir(new File(pathOfCurrentFile), filePaths);
		for(String r : read){
			System.out.println("registry "+r);
		}
		for (String file : filePaths) {
			if (!read.contains(new File(file).getAbsolutePath())) {
				System.out.println(new File(file).getAbsolutePath()+ " created after hash!");

			}
		}
		//second check paths from registry file and consider if there is deleted file or
		// any changed file

		for (String line : readFile.lines) {
			checkFile(line.split(" ")[0], line.split(" ")[1]);
		}
	}

	private void checkFile(String filePath, String hashValueFilePath) {
		File originalFile = new File(filePath);
		File hashValueFile = new File(hashValueFilePath);
		String dataHashValueFile = null, dataOrginalFile = null;
		if (!originalFile.exists()) {
			System.out.println(filePath + " file couldn't find! Deleted!");
			
		}
		if(!hashValueFile.exists()){
			System.out.println(hashValueFilePath + " file couldn't find! Deleted!");
			
		}
		
		try {
			dataOrginalFile = new String(Files.readAllBytes(Paths.get(filePath)));
			dataHashValueFile = new String(Files.readAllBytes(Paths.get(hashValueFilePath)));
		} catch (IOException e) {
			return;
		}
		if (!isFileEqualtoItsOldForm(dataOrginalFile, dataHashValueFile)) {
			System.out.println(filePath + " is Changed!");
		}
	}

	private boolean isFileEqualtoItsOldForm(String data, String hashValue) {
		String hashValueOfData = null; // it will be hash value of data after
		// getting hash value
		//
		// hashValueOfData=data.getHash(data);
		//
		return data.equals(hashValue);
	}

	private void getListofFilesInADir(File file, ArrayList<String> filesPaths) {
		for (File f : file.listFiles()) {
			if (f.isDirectory()) {
				getListofFilesInADir(f, filesPaths);
			} else {
				filesPaths.add(f.getPath());
			}
		}
	}
	private ArrayList<String> getPathsFromRegistry(ReadFile read){
		ArrayList<String> paths = new ArrayList<>();
		for(String l :read.lines){
			paths.add(l.split(" ")[0]);
			paths.add(l.split(" ")[1]);
		}
		return paths; 
	}
}