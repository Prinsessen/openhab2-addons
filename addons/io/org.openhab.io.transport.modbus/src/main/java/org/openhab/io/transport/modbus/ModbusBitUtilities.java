package org.openhab.io.transport.modbus;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Optional;

import org.eclipse.smarthome.core.library.types.DecimalType;
import org.eclipse.smarthome.core.library.types.OnOffType;
import org.eclipse.smarthome.core.library.types.OpenClosedType;
import org.eclipse.smarthome.core.types.Command;

public class ModbusBitUtilities {
    static final public String VALUE_TYPE_BIT = "bit";
    static final public String VALUE_TYPE_INT8 = "int8";
    static final public String VALUE_TYPE_UINT8 = "uint8";
    static final public String VALUE_TYPE_INT16 = "int16";
    static final public String VALUE_TYPE_UINT16 = "uint16";
    static final public String VALUE_TYPE_INT32 = "int32";
    static final public String VALUE_TYPE_UINT32 = "uint32";
    static final public String VALUE_TYPE_FLOAT32 = "float32";
    static final public String VALUE_TYPE_INT32_SWAP = "int32_swap";
    static final public String VALUE_TYPE_UINT32_SWAP = "uint32_swap";
    static final public String VALUE_TYPE_FLOAT32_SWAP = "float32_swap";

    private static String[] VALUE_TYPE_8BIT = new String[] { VALUE_TYPE_INT8, VALUE_TYPE_UINT8 };

    private static String[] VALUE_TYPE_16BIT = new String[] { VALUE_TYPE_INT16, VALUE_TYPE_UINT16 };

    private static String[] VALUE_TYPE_32BIT = new String[] { VALUE_TYPE_FLOAT32, VALUE_TYPE_INT32, VALUE_TYPE_UINT32,
            VALUE_TYPE_INT32_SWAP, VALUE_TYPE_UINT32_SWAP, VALUE_TYPE_FLOAT32_SWAP };

    /**
     * Read data from registers and convert the result to DecimalType
     * Interpretation of <tt>index</tt> goes as follows depending on type
     *
     * BIT:
     * - a single bit is read from the registers
     * - indices between 0...15 (inclusive) represent bits of the first register
     * - indices between 16...31 (inclusive) represent bits of the second register, etc.
     * - index 0 refers to the least significant bit of the first register
     * - index 1 refers to the second least significant bit of the first register, etc.
     * INT8:
     * - a byte (8 bits) from the registers is interpreted as signed integer
     * - index 0 refers to low byte of the first register, 1 high byte of first register
     * - index 2 refers to low byte of the second register, 3 high byte of second register, etc.
     * - it is assumed that each high and low byte is encoded in most significant bit first order
     * UINT8:
     * - same as INT8 except values are interpreted as unsigned integers
     * INT16:
     * - register with index (counting from zero) is interpreted as 16 bit signed integer.
     * - it is assumed that each register is encoded in most significant bit first order
     * UINT16:
     * - same as INT16 except values are interpreted as unsigned integers
     * INT32:
     * - registers (index) and (index + 1) are interpreted as signed 32bit integer.
     * - it assumed that the first register contains the most significant 16 bits
     * - it is assumed that each register is encoded in most significant bit first order
     * INT32_SWAP:
     * - Same as INT32 but registers swapped
     * UINT32:
     * - same as UINT32 except values are interpreted as unsigned integers
     * UINT32_SWAP:
     * - Same as UINT32 but registers swapped
     * FLOAT32:
     * - registers (index) and (index + 1) are interpreted as signed 32bit floating point number.
     * - it assumed that the first register contains the most significant 16 bits
     * - it is assumed that each register is encoded in most significant bit first order
     * FLOAT32_SWAP:
     * - Same as FLOAT32 but registers swapped
     *
     * @param registers
     *            list of registers, each register represent 16bit of data
     * @param index
     *            zero based item index. Interpretation of this depends on type
     * @param type
     *            item type, e.g. unsigned 16bit integer (<tt>ModbusBindingProvider.VALUE_TYPE_UINT16</tt>)
     * @return number representation queried value
     * @throws IllegalArgumentException when <tt>type</tt> does not match a known type
     * @throws IndexOutOfBoundsException when <tt>index</tt> is out of bounds of registers
     *
     */
    public static DecimalType extractStateFromRegisters(ModbusRegisterArray registers, int index, String type) {
        if (type.equals(VALUE_TYPE_BIT)) {
            return new DecimalType((registers.getRegister(index / 16).toUnsignedShort() >> (index % 16)) & 1);
        } else if (type.equals(VALUE_TYPE_INT8)) {
            return new DecimalType(registers.getRegister(index / 2).getBytes()[1 - (index % 2)]);
        } else if (type.equals(VALUE_TYPE_UINT8)) {
            return new DecimalType((registers.getRegister(index / 2).toUnsignedShort() >> (8 * (index % 2))) & 0xff);
        } else if (type.equals(VALUE_TYPE_INT16)) {
            ByteBuffer buff = ByteBuffer.allocate(2);
            buff.put(registers.getRegister(index).getBytes());
            return new DecimalType(buff.order(ByteOrder.BIG_ENDIAN).getShort(0));
        } else if (type.equals(VALUE_TYPE_UINT16)) {
            return new DecimalType(registers.getRegister(index).toUnsignedShort());
        } else if (type.equals(VALUE_TYPE_INT32)) {
            ByteBuffer buff = ByteBuffer.allocate(4);
            buff.put(registers.getRegister(index).getBytes());
            buff.put(registers.getRegister(index + 1).getBytes());
            return new DecimalType(buff.order(ByteOrder.BIG_ENDIAN).getInt(0));
        } else if (type.equals(VALUE_TYPE_UINT32)) {
            ByteBuffer buff = ByteBuffer.allocate(8);
            buff.position(4);
            buff.put(registers.getRegister(index).getBytes());
            buff.put(registers.getRegister(index + 1).getBytes());
            return new DecimalType(buff.order(ByteOrder.BIG_ENDIAN).getLong(0));
        } else if (type.equals(VALUE_TYPE_FLOAT32)) {
            ByteBuffer buff = ByteBuffer.allocate(4);
            buff.put(registers.getRegister(index).getBytes());
            buff.put(registers.getRegister(index + 1).getBytes());
            return new DecimalType(buff.order(ByteOrder.BIG_ENDIAN).getFloat(0));
        } else if (type.equals(VALUE_TYPE_INT32_SWAP)) {
            ByteBuffer buff = ByteBuffer.allocate(4);
            buff.put(registers.getRegister(index + 1).getBytes());
            buff.put(registers.getRegister(index).getBytes());
            return new DecimalType(buff.order(ByteOrder.BIG_ENDIAN).getInt(0));
        } else if (type.equals(VALUE_TYPE_UINT32_SWAP)) {
            ByteBuffer buff = ByteBuffer.allocate(8);
            buff.position(4);
            buff.put(registers.getRegister(index + 1).getBytes());
            buff.put(registers.getRegister(index).getBytes());
            return new DecimalType(buff.order(ByteOrder.BIG_ENDIAN).getLong(0));
        } else if (type.equals(VALUE_TYPE_FLOAT32_SWAP)) {
            ByteBuffer buff = ByteBuffer.allocate(4);
            buff.put(registers.getRegister(index + 1).getBytes());
            buff.put(registers.getRegister(index).getBytes());
            return new DecimalType(buff.order(ByteOrder.BIG_ENDIAN).getFloat(0));
        } else {
            throw new IllegalArgumentException(type);
        }
    }

    /**
     * Calculates boolean value that will be written to the device as a result of OpenHAB command
     * Used with item bound to "coil" type slaves
     *
     * @param command OpenHAB command received by the item
     * @return new boolean value to be written to the device
     */
    public static Optional<Boolean> translateCommand2Boolean(Command command) {
        if (command.equals(OnOffType.ON)) {
            return Optional.of(Boolean.TRUE);
        }
        if (command.equals(OnOffType.OFF)) {
            return Optional.of(Boolean.FALSE);
        }
        if (command.equals(OpenClosedType.OPEN)) {
            return Optional.of(Boolean.TRUE);
        }
        if (command.equals(OpenClosedType.CLOSED)) {
            return Optional.of(Boolean.FALSE);
        }
        if (command instanceof DecimalType) {
            return Optional.of(!command.equals(DecimalType.ZERO));
        }
        return Optional.empty();
    }

    public static int getBitCount(String valueType) {
        if (Arrays.asList(VALUE_TYPE_32BIT).contains(valueType)) {
            return 32;
        } else if (Arrays.asList(VALUE_TYPE_16BIT).contains(valueType)) {
            return 16;
        } else if (Arrays.asList(VALUE_TYPE_8BIT).contains(valueType)) {
            return 8;
        } else if ("bit".equals(VALUE_TYPE_BIT)) {
            return 1;
        } else {
            throw new IllegalArgumentException(valueType);
        }
    }

}