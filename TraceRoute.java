import org.shortpasta.icmp2.IcmpPingRequest;
import org.shortpasta.icmp2.IcmpPingResponse;
import org.shortpasta.icmp2.IcmpPingUtil;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;

//Class TraceRouteUtility
public class TraceRoute {

    TraceRoute(InetAddress ipAddr, PrintWriter pr) throws UnknownHostException {
        this.ipAddr = ipAddr;
        this.pr = pr;
    }

    final private InetAddress ipAddr;
    final private PrintWriter pr;
    private int ttl = 1;
    private int fails = 0;
    private boolean reachedDestination = false;
    private final int maxHops = 30;
    private String tracerouteReport = "Traceroute Report:";

    //get the number of hops between nodes
    public int getNumberOfHopsBetweenNodes(){
        System.out.println("\nTraceroute Report:");

        while(!reachedDestination && ttl < maxHops) {
            sendPing();
            ttl=ttl+1;
        }
        ttl=ttl-1;
        System.out.println("\nFinished doing traceroute with hops " + ttl);
        return ttl;
    }

    //sends ICMP packets to the target node
    private void sendPing() {
        IcmpPingRequest icmpReqst = IcmpPingUtil.createIcmpPingRequest();
        icmpReqst.setHost(ipAddr.getHostAddress());
        icmpReqst.setTtl(ttl);

        IcmpPingResponse response = IcmpPingUtil.executePingRequest(icmpReqst);
        if(response.getTimeoutFlag()) {
            fails=fails+1;
            String output = "Request timed-out at" + ttl;
            System.out.println(output);
        }

        String currentResult = ttl  + "   " + response.getRtt() + " ms   " + response.getHost();
        System.out.println(currentResult);

        tracerouteReport = tracerouteReport + System.getProperty("line.separator") + currentResult;

        if(response.getSuccessFlag()) {
            reachedDestination = true;


        }
    }
}