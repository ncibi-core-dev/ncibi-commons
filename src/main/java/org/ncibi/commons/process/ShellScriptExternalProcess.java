package org.ncibi.commons.process;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.ncibi.commons.io.InputStreamLineProcessor;

public class ShellScriptExternalProcess
{
    private final String command;
    private final List<String> args;
    private final boolean isUnixSystem;

    public ShellScriptExternalProcess(String command, List<String> args)
    {
        this.command = command;
        this.args = args;
        String os = System.getProperty("os.name");
        if (os != null && os.contains("Windows"))
        {
            isUnixSystem = false;
        }
        else
        {
            isUnixSystem = true;
        }
    }

    public List<String> run(int countLinesToIgnore)
    {
        Process p = createAndStartProcessUsingShToRunCommand();
        return readOutputFromProcess(p, countLinesToIgnore);
    }

    private Process createAndStartProcessUsingShToRunCommand()
    {
        Process p = null;

        if (!isUnixSystem)
        {
            p = createProcessAndCommandForWindows();
        }
        else
        {
            p = createProcessAndCommandForUnix();
        }

        return p;
    }

    private Process createProcessAndCommandForWindows()
    {
        String cmd = createCommandAsOneStringSpaceSeparatedAndQuoted();
        ProcessBuilder pb = new ProcessBuilder("sh", "-c", cmd);
        return startProcess(pb);
    }

    private String createCommandAsOneStringSpaceSeparatedAndQuoted()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("\"");
        sb.append(command + " ");
        for (String arg : args)
        {
            sb.append(arg);
        }
        sb.append("\"");
        return sb.toString();
    }

    private Process createProcessAndCommandForUnix()
    {
        List<String> cmd = new ArrayList<String>();
        cmd.add(command);
        cmd.addAll(args);
        ProcessBuilder pb = new ProcessBuilder(cmd);
        return startProcess(pb);
    }

    private Process startProcess(ProcessBuilder pb)
    {
        Process p = null;
        try
        {
            p = pb.start();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return p;
    }

    private List<String> readOutputFromProcess(Process p, int countLinesToIgnore)
    {
        final List<String> lines = new LinkedList<String>();
        InputStreamLineProcessor processor = new InputStreamLineProcessor()
        {
            @Override
            public void processLine(String line) throws IOException
            {
                lines.add(line.trim());
            }
        };

        if (countLinesToIgnore > 0)
        {
            processor.setHeaderLineCount(countLinesToIgnore);
            processor.setSkipHeader(true);
        }
        processor.process(p.getInputStream());
        return lines;
    }
}
