import be.technifutur.java2021.api.ApplicationFactory;

/**
 * open module myFrameWork {
 *     requires org.apache.logging.log4j;
 *     uses ApplicationFactory;
 *     exports be.technifutur.java2021.api;
 * }
 */
open module myFrameWork {
    requires org.apache.logging.log4j;
    uses ApplicationFactory;
    exports be.technifutur.java2021.api;
}