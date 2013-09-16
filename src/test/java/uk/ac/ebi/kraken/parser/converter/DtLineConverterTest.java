package uk.ac.ebi.kraken.parser.converter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.TestCase;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import uk.ac.ebi.kraken.interfaces.uniprot.EntryAudit;
import uk.ac.ebi.uniprot.parser.impl.dt.DtLineConverter;
import uk.ac.ebi.uniprot.parser.impl.dt.DtLineObject;

public class DtLineConverterTest {
	private DtLineConverter converter = new DtLineConverter();
	@Test
	public void testConverter() throws Exception{
		DtLineObject dtLine = new DtLineObject();
	
		DateTimeFormatter formatter = DateTimeFormat.forPattern("dd-MMM-yyyy");
		dtLine.integration_date = DateTime.parse("28-JUN-2011", formatter);
		dtLine.seq_date = DateTime.parse("19-JUL-2004", formatter);
		dtLine.seq_version =1;
		dtLine.entry_date = DateTime.parse("18-APR-2012", formatter);
		dtLine.entry_version =24;
		EntryAudit enAudit = converter.convert(dtLine);
		TestCase.assertEquals(1, enAudit.getSequenceVersion());
		TestCase.assertEquals(24, enAudit.getEntryVersion());

		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		Date date = dateFormat.parse("28-JUN-2011");
		TestCase.assertEquals(date, enAudit.getFirstPublicDate());
		
	}
}
