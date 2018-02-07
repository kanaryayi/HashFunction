import java.util.ArrayList;
import java.util.List;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.MalformedInputException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static java.lang.System.out;

public class ReadFile {
	public List<String> lines;
	public int size;

	public ReadFile(String path) {
		try {
			size = Files.readAllLines(Paths.get(path)).size();
			lines = Files.readAllLines(Paths.get(path));
		} catch (MalformedInputException e) {
			System.out.println("burada");
			try {
				String word;
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "ISO-8859-9"));
				lines = new ArrayList<>();
				while((word=br.readLine())!=null){
					lines.add(word);
				}
				size = lines.size();
			} catch (UnsupportedEncodingException | FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (IOException e) {
			System.out.println("EQUAL WORDS! ADD NEW WORDS");
			out.println(path);
			size = 0;
			lines = null;
			e.printStackTrace();
		}
	}
}
