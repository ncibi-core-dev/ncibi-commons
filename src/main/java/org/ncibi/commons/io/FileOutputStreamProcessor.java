package org.ncibi.commons.io;

import java.io.FileOutputStream;
import java.io.IOException;

public abstract class FileOutputStreamProcessor
{
    public abstract void processStream(FileOutputStream stream);
    
    private FileStreamProcessor<FileOutputStream> outputStream = new FileStreamProcessor<FileOutputStream>()
    {
        @Override
        public void closeFileStream(FileOutputStream stream) throws IOException
        {
            stream.close();
        }

        @Override
        public FileOutputStream openFileStream(String filename) throws IOException
        {
            return new FileOutputStream(filename);
        }

        @Override
        public void processFileStream(FileOutputStream stream)
        {
            processStream(stream);
        }   
    };
    
    public void process(String filename)
    {
        outputStream.process(filename);
    }
}
