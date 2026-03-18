package br.gov.caixa.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * @author c101482 - Vinicius Ferraz Campos Florentino
 */
public final class FileUtil {

	public static final int DEFAULT_STREAM_BUFFER_SIZE = 10240;

	// Colocado somente para dar 100% no emma
	@SuppressWarnings("unused")
	private static FileUtil instancia = new FileUtil();

	private FileUtil() {
	}
	
	
	/**
	 * @param f
	 * @param lv
	 * @return
	 */
	public static int processeFileLines(File f, LinhaVisitor lv) {
		return processeFileLines(f, lv, false);
	}
	
	/**
	 * @param f
	 * @param lv
	 * @param lockFile true caso queira que faça o lock do arquivo.
	 * 
	 * @return quantidade de linhas processadas. -1 caso nao tenha obtido o lock
	 */
	public static int processeFileLines(File f, LinhaVisitor lv, boolean lockFile) {
		Scanner scanner = null;
		FileInputStream fileInputStream = null;
		FileLock lock = null;
		RandomAccessFile randomAccessFile = null;
		try {
			
			if (lockFile) {
				randomAccessFile = new RandomAccessFile(f, "rw");
				fileInputStream = new FileInputStream(randomAccessFile.getFD());
				lock = randomAccessFile.getChannel().tryLock();
				if(lock == null) {
					return -1;
				}
				scanner = new Scanner(fileInputStream, CharsetUtil.UTF8.name());
				
			} else {
				scanner = new Scanner(f, CharsetUtil.UTF8.name());
				
			}
			int cont = 0;
			while(scanner.hasNextLine()) {
				String line = scanner.nextLine();
				lv.visite(cont, line);
				cont++;
			}
			return cont;
		} catch (RuntimeException re) {
			throw re;
		} catch (Exception e) {
			throw createErroAoLerArquivo(e);
		} finally {
			CloseableUtil.close(scanner);
			CloseableUtil.close(randomAccessFile);
			CloseableUtil.close(fileInputStream);
			if(lock != null) {
				try {
					lock.release();
				} catch (IOException e) {
					//Como nao tem log, nada é feito
				}
			}
		}
	}


	public static String read(File file) throws FileNotFoundException {
		return read(file, CharsetUtil.UTF8.name());
	}

	public static String read(File file, String charsetName) throws FileNotFoundException {
		return readScanner(new Scanner(file, charsetName));
	}

	private static String readScanner(Scanner scanner) {
		StringBuilder sb = new StringBuilder();
		try {

			while (scanner.hasNextLine()) {
				sb.append(scanner.nextLine()).append('\n');
			}
		} catch (RuntimeException re) {
			throw re;
		} catch (Exception e) {
			throw createErroAoLerArquivo(e);
		} finally {
			scanner.close();
		}
		return sb.toString();
	}

	private static IllegalStateException createErroAoLerArquivo(Exception e) {
		return new IllegalStateException("Erro ao ler arquivo", e);
	}

	public static List<String> readNotEmptyLines(InputStream is) throws FileNotFoundException {
		return readNotEmptyLines(new Scanner(is, CharsetUtil.UTF8.name()));
	}

	private static List<String> readNotEmptyLines(Scanner scanner) {
		List<String> list = new ArrayList<String>();
		try {
			while (scanner.hasNextLine()) {
				String nextLine = scanner.nextLine();
				if (!StringUtil.isEmpty(nextLine)) {
					list.add(nextLine);
				}
			}
		} catch (RuntimeException re) {
			throw re;
		} catch (Exception e) {
			throw createErroAoLerArquivo(e);
		} finally {
			CloseableUtil.close(scanner);
		}
		return list;
	}
	
	public static void moveAndDeleteSource(File source, File dest) throws IOException {
		FileChannel srcChannel = null;
		FileChannel dstChannel = null;
		RandomAccessFile sourceRandomAccessFile = null;
		FileOutputStream destFileOutputStream = null;
		boolean sucesso = false;
		try {
			// source file channel
			// return the unique FileChannel object associated with this file input stream.
			sourceRandomAccessFile = new RandomAccessFile(source, "rw");
			srcChannel = sourceRandomAccessFile.getChannel();
			
			// destination file channel
			// return the unique FileChannel object associated with this file output stream.
			destFileOutputStream = new FileOutputStream(dest);
			dstChannel = destFileOutputStream.getChannel();

			move(srcChannel, dstChannel);
			sucesso = true;
		} finally {
			CloseableUtil.close(srcChannel);
			CloseableUtil.close(dstChannel);
			CloseableUtil.close(sourceRandomAccessFile);
			CloseableUtil.close(destFileOutputStream);
			if (sucesso) {
				source.delete();
			}
		}
	}
	
	public static void move(FileChannel srcChannel, FileChannel dstChannel) throws IOException {
		try {
			dstChannel.truncate(srcChannel.size());
			// transfer bytes into this channel's file from the given readable byte channel
			dstChannel.transferFrom(srcChannel, 0, srcChannel.size());

		} finally {
			// close channels
			CloseableUtil.close(srcChannel);
			CloseableUtil.close(dstChannel);
		}
	}

	public static Boolean copieDadosArquivo(File arqOrigem, File arqDestino, Boolean sobreEscreve) {

		if (arqOrigem.isFile()) {


			try {

				// Abre os streams de dados
				stream(new BufferedInputStream(new FileInputStream(arqOrigem)), new BufferedOutputStream(new FileOutputStream(arqDestino,
						!sobreEscreve)));


				// Sucesso
				return true;

			} catch (IOException e) {
				return false;
			}

		}

		return false;

	}
	public static String read(InputStream is) {
		if (is == null) {
			throw new IllegalArgumentException("InputStream is null");
		}
		return readScanner(new Scanner(is, CharsetUtil.UTF8.name()));
	}

	public static String read(Class<?> c, String fileName) {
		return read(c.getResourceAsStream(fileName));
	}

	public static File createTempFile(String prefix, String suffix) {
		try {
			return File.createTempFile(prefix, suffix);
		} catch (IOException e) {
			throw new IllegalStateException("Erro ao criar arquivo temporário: " + prefix + " " + suffix, e);
		}
	}

	public static File createTempDirectory(String prefix) {
		try {
			File createTempFile = File.createTempFile(prefix, null);
			createTempFile.mkdir();
			return createTempFile;
		} catch (IOException e) {
			throw new IllegalStateException("Erro ao criar arquivo temporário: " + prefix, e);
		}
	}
	
	public static String calcHash(File file) {
		try {
			return calcHash(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			throw new IllegalStateException("Erro ao calcular hash: ", e);
		}
	}
	
	public static String calcHash(String str) {
		try {
			return calcHash(new ByteArrayInputStream(str.getBytes(CharsetUtil.UTF8.name())));
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("Erro ao calcular hash: ", e);
		}
	}
	
	public static String calcHash(InputStream is) {
		try {
			byte[] dataBytes = new byte[1024];
			int nread = 0;
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			while ((nread = is.read(dataBytes)) != -1) {
				md.update(dataBytes, 0, nread);
			}
			byte[] mdbytes = md.digest();
			// convert the byte to hex format
			Appendable sb = new StringBuilder("");
			for (int i = 0; i < mdbytes.length; i++) {
				sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			return sb.toString();
		} catch (Exception e) {
			throw new IllegalStateException("Erro ao calcular hash: ", e);
		} finally {
			CloseableUtil.close(is);
		}
	}

	public static long stream(InputStream input, OutputStream output) throws IOException {
		ReadableByteChannel inputChannel = null;
		WritableByteChannel outputChannel = null;

		try {
			inputChannel = Channels.newChannel(input);
			outputChannel = Channels.newChannel(output);
			ByteBuffer buffer = ByteBuffer.allocateDirect(DEFAULT_STREAM_BUFFER_SIZE);
			long size = 0;

			while (inputChannel.read(buffer) != -1) {
				buffer.flip();
				size += outputChannel.write(buffer);
				buffer.clear();
			}

			return size;
		} finally {
			CloseableUtil.close(outputChannel);
			CloseableUtil.close(inputChannel);
		}
	}
	
	/**
	 * @param f
	 * @param inicio
	 * @param quantidade
	 * @return
	 * @throws IOException
	 */
	public static List<String> read(File f, int inicio, int quantidade) throws IOException {
		return read(f, CharsetUtil.UTF8, inicio, quantidade);
	}

	public static List<String> read(File f, Charset charset, int inicio, int quantidade) throws IOException {
		List<String> linhas = new ArrayList<String>();
		Scanner scanner = new Scanner(f, charset.name());
		try {
		int linhaAtual = 0;
		while(linhaAtual < inicio) {
			scanner.nextLine();
			linhaAtual++;
		}
		
		for (int i = 0; i < quantidade && scanner.hasNextLine(); i++) {
			linhas.add(scanner.nextLine());
		}
		} finally {
			CloseableUtil.close(scanner);
		}
		return linhas;
	}
	
	public static boolean validePath(String containsString, File filePath) {
		try {
			String contains = StringUtil.toLowerCase(containsString);
			String canonicalPath = StringUtil.toLowerCase(filePath.getCanonicalPath());
			return canonicalPath.contains(contains);
		} catch (IOException e) {
			return false;
		}
	}
	
	public static void delete(File file) {
		if (file.isFile()) {
			file.delete();
		} else if (file.isDirectory()) {
			for (File subFile : file.listFiles()) {
				delete(subFile);
				file.delete();
			}
		}
	}
	
	public static void zip(File zip, File file) throws IOException {
		ZipOutputStream zos = null;
		try {
			String name = file.getName();
			zos = new ZipOutputStream(new FileOutputStream(zip));

			ZipEntry entry = new ZipEntry(name);
			zos.putNextEntry(entry);

			FileInputStream fis = null;
			try {
				fis = new FileInputStream(file);
				byte[] byteBuffer = new byte[1024];
				int bytesRead = -1;
				while ((bytesRead = fis.read(byteBuffer)) != -1) {
					zos.write(byteBuffer, 0, bytesRead);
				}
				zos.flush();
			} finally {
				CloseableUtil.close(fis);
			}
			zos.closeEntry();

			zos.flush();
		} finally {
			CloseableUtil.close(zos);
		}
	}

	public static void unzip(File zip, File folder) throws IOException {

		byte[] buffer = new byte[1024];

		// create output directory is not exists
		if (!folder.exists()) {
			folder.mkdir();
		}

		ZipInputStream zis = null;
		try {
			// get the zip file content
			zis = new ZipInputStream(new FileInputStream(zip));
			// get the zipped file list entry
			ZipEntry ze = zis.getNextEntry();

			while (ze != null) {
				String fileName = ze.getName();
				File newFile = new File(folder.getPath() + File.separator + fileName);

				// create all non exists folders
				// else you will hit FileNotFoundException for compressed folder
				new File(newFile.getParent()).mkdirs();

				FileOutputStream fos = null;
				try {
					fos = new FileOutputStream(newFile);

					int len;
					while ((len = zis.read(buffer)) > 0) {
						fos.write(buffer, 0, len);
					}
				} finally {
					CloseableUtil.close(fos);
				}

				ze = zis.getNextEntry();
			}

			zis.closeEntry();
		} finally {
			CloseableUtil.close(zis);
		}
	}

	public interface LinhaVisitor {

		/**
		 * 
		 * @param lineNumber
		 * @param line
		 */
		public void visite(int lineNumber, String line);
	}

}
