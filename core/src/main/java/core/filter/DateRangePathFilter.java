package core.filter;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.PathFilter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateRangePathFilter implements PathFilter{

    private final Pattern PATTERN = Pattern.compile("^.*/(\\d\\d\\d\\d/\\d\\d/\\d\\d).*$");
    private final Date start, end;

    public DateRangePathFilter(Date start, Date end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public boolean accept(Path path) {
        Matcher matcher = PATTERN.matcher(path.toString());
        if (matcher.matches()){
            DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
            try {
                return inIntervel(format.parse(matcher.group(1)));
            } catch (ParseException e) {
                return false;
            }
        }
        return false;
    }

    private boolean inIntervel(Date date){
        return !date.before(start) && !date.after(end);
    }
}
