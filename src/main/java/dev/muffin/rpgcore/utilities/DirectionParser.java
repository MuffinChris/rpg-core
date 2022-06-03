package dev.muffin.rpgcore.utilities;

import org.bukkit.block.BlockFace;

public class DirectionParser {

    private static final BlockFace[] axis = { BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST };
    private static final BlockFace[] radial = { BlockFace.NORTH, BlockFace.NORTH_EAST, BlockFace.EAST, BlockFace.SOUTH_EAST, BlockFace.SOUTH, BlockFace.SOUTH_WEST, BlockFace.WEST, BlockFace.NORTH_WEST };

    public static BlockFace yawToFace(float yaw, boolean useSubCardinalDirections) {
        if (useSubCardinalDirections)
            return radial[Math.round(yaw / 45f) & 0x7].getOppositeFace();

        return axis[Math.round(yaw / 90f) & 0x3].getOppositeFace();
    }

    public static String yawToString(float yaw) {
        BlockFace bf = radial[Math.round(yaw / 45f) & 0x7].getOppositeFace();
        if (bf == BlockFace.NORTH) {
            return "N";
        }
        if (bf == BlockFace.NORTH_WEST) {
            return "NW";
        }
        if (bf == BlockFace.NORTH_EAST) {
            return "NE";
        }
        if (bf == BlockFace.SOUTH) {
            return "S";
        }
        if (bf == BlockFace.SOUTH_EAST) {
            return "SE";
        }
        if (bf == BlockFace.SOUTH_WEST) {
            return "SW";
        }
        if (bf == BlockFace.EAST) {
            return "E";
        }
        if (bf == BlockFace.WEST) {
            return "W";
        }
        return "";
    }
}
