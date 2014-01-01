package com.github.barteks2x.b173gen.test;

import com.github.barteks2x.b173gen.exception.B173GenInitException;
import com.github.barteks2x.b173gen.exception.B173GenInitWarning;
import com.github.barteks2x.b173gen.reflection.Util;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.*;
import static org.junit.Assert.*;

public class ReflectionTest {

    @BeforeClass
    public static void setUpClass() {
        Object o = net.minecraft.server.v1_7_R1.Block.class;//force java to load these classes
        o = net.minecraft.server.v1_7_R1.World.class;
        o = org.bukkit.craftbukkit.v1_7_R1.CraftWorld.class;
    }

    @AfterClass
    public static void tearDownClass() {
    }

    public ReflectionTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void reflectionUtilInitTest() {

        LinkedList<Exception> errors = new LinkedList<Exception>();
        LinkedList<B173GenInitWarning> warnings = new LinkedList<B173GenInitWarning>();
        Util.init(errors, warnings);
        for(Exception ex: errors) {
            if(ex instanceof B173GenInitException) {
                B173GenInitException e = (B173GenInitException)ex;
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                StringBuilder sb = new StringBuilder("173generator init exception: ")
                        .append(e.getAdditionalInfo()).append("\n")
                        .append(sw.toString());
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, sb.toString());
            } else {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                ex.printStackTrace(pw);
                StringBuilder sb
                        = new StringBuilder("173generator init exception: ")
                        .append("\n").append(sw.toString());
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, sb.toString());
            }
        }
        for(B173GenInitWarning w: warnings) {
            StringBuilder sb = new StringBuilder("173generator init warning: ").append("\n")
                    .append(w.toString());
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, sb.toString());
        }
        if(!errors.isEmpty()) {
            fail("Caught exception(s) durung initialization!");
        }
        if(!warnings.isEmpty()) {
            fail("Found warning(s) durung initialization!");
        }
    }
}
