import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.*;
import game.Console;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;



public class RestfulAPI {
    static final String xApiKeyName = "x-api-key";
    static final String xApiKeyValue = "6f9a0690dbb568ca5333";
    static final String userIdName = "userId";
    static final String userIdValue = "778";
    static String gameId;
    static String teamId = "1190";
    static String moveId;
    static String move;
    static String nextMove;
    public static void main(String[] args) {
        String result = "";
        Scanner scanner = new Scanner(System.in);
        System.out.println("please enter oponent's teamId: ");
        String teamId2 = scanner.nextLine();
        System.out.println("please enter the boardSize and target");
        String boardSize = scanner.nextLine();
        String target = scanner.nextLine();

        Console ticTacToe = new Console(Integer.parseInt(boardSize), Integer.parseInt(target));
        System.out.println("please select 1 or 2, 1 is create game and 2 is paly a game");
        String option = scanner.nextLine();
        if (option.equals("1")) {
            System.out.println("creating game....");
            createGame(teamId, teamId2, boardSize, target);
            System.out.println("you can play the game by use this gameId: " + gameId);

            nextMove = ticTacToe.playMove("");
            System.out.println("returned move: " + nextMove);
            makeMove(gameId, teamId, nextMove);
            int timeout = 0;
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                timeout++;
                if (timeout > 120) {
                    System.out.println("timeout");
                    break;
                }
                if (getMoves(gameId)) {
                    nextMove = ticTacToe.playMove(move);
                    System.out.println("Returned move: " + nextMove);
                    if (nextMove.equals("WIN") || nextMove.equals("LOSE") || nextMove.equals("DRAW")) {

                        System.out.print(nextMove);
                        break;
                    }
                    makeMove(gameId, teamId, nextMove);
                    result = ticTacToe.getGameResult();
                    if (result.equals("WIN") || result.equals("LOSE") || result.equals("DRAW")) {

                        System.out.print(nextMove);
                        break;
                    }
                    timeout = 0;
                }
            }
        } else {
            System.out.println("please enter the gameId you want to join: ");
            gameId = scanner.nextLine();
            //System.out.println("returned move: " + nextMove);
            //makeMove(gameId, teamId, nextMove);
            int timeout = 0;
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                timeout++;
                if (timeout > 120) {
                    System.out.println("timeout");
                    break;
                }
                if (getMoves(gameId)) {
                    nextMove = ticTacToe.playMove(move);
                    System.out.println("Returned move: " + nextMove);
                    if (nextMove.equals("WIN") || nextMove.equals("LOSE") || nextMove.equals("DRAW")) {

                        System.out.print(nextMove);
                        break;
                    }
                    makeMove(gameId, teamId, nextMove);
                    result = ticTacToe.getGameResult();
                    if (result.equals("WIN") || result.equals("LOSE") || result.equals("DRAW")) {

                        System.out.print(nextMove);
                        break;
                    }
                    timeout = 0;
                }
            }
        }
//        Console ticTacToe = new Console(5, 3);
//        //createGame("1128", "1129", "5", "3");
//        nextMove = ticTacToe.playMove("");
//        System.out.println("returned move: " + nextMove);
//        makeMove(gameId, "1128", nextMove);
//        String result = "";
//        int timeout = 0;
//        while (true) {
//            try {
//                TimeUnit.SECONDS.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            timeout++;
//            if (timeout > 120) {
//                System.out.println("timeout");
//                break;
//            }
//            if (getMoves(gameId)) {
//                nextMove = ticTacToe.playMove(move);
//                System.out.println("Returned move: " + nextMove);
//                if (nextMove.equals("WIN") || nextMove.equals("LOSE") || nextMove.equals("DRAW")) {
//
//                    System.out.print(nextMove);
//                    break;
//                }
//                makeMove(gameId, teamId, nextMove);
//                result = ticTacToe.getGameResult();
//                if (result.equals("WIN") || result.equals("LOSE") || result.equals("DRAW")) {
//
//                    System.out.print(nextMove);
//                    break;
//                }
//                timeout = 0;
//            }
//        }
    }

    //code : OK   teamId : "1128"
    public static boolean createTeam(String name) {
        //{"code":"OK","teamId":1125}
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String entityStr = null;
        CloseableHttpResponse response = null;

        try {
            HttpPost httpPost = new HttpPost("http://www.notexponential.com/aip2pgaming/api/index.php");

            List<NameValuePair> list = new LinkedList<NameValuePair>();
            BasicNameValuePair param1 = new BasicNameValuePair("name", name);
            BasicNameValuePair param2 = new BasicNameValuePair("type", "team");
            list.add(param1);
            list.add(param2);
            // 使用URL实体转换工具
            UrlEncodedFormEntity entityParam = new UrlEncodedFormEntity(list, "UTF-8");
            httpPost.setEntity(entityParam);


            httpPost.addHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.7.6)");
            httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
            httpPost.addHeader(xApiKeyName, xApiKeyValue);
            httpPost.addHeader(userIdName, userIdValue);

            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            entityStr = EntityUtils.toString(entity, "UTF-8");
        } catch (ClientProtocolException e) {
            System.err.println("ClientProtocolException");
            e.printStackTrace();
        } catch (ParseException e) {
            System.err.println("ParseException");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("IOException");
            e.printStackTrace();
        } finally {
            if (null != response) {
                try {
                    response.close();
                    httpClient.close();
                } catch (IOException e) {
                    System.err.println("IOException1");
                    e.printStackTrace();
                }
            }
        }

        Map map = JSON.parseObject(entityStr, Map.class);
        if (map.get("code").equals("FAIL")) {
            System.out.println(map.get("message"));
            return false;
        } else {
            teamId = String.valueOf(map.get("teamId"));
            System.out.println("team created successfully, and the teamId is " + teamId);
            return true;
        }
    }

    //code : OK
    public static boolean addTeamMember(String teamId, String userId) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String entityStr = null;
        CloseableHttpResponse response = null;

        try {
            HttpPost httpPost = new HttpPost("http://www.notexponential.com/aip2pgaming/api/index.php");
            List<NameValuePair> list = new LinkedList<NameValuePair>();
            BasicNameValuePair param1 = new BasicNameValuePair("teamId", teamId);
            BasicNameValuePair param2 = new BasicNameValuePair("type", "member");
            BasicNameValuePair param3 = new BasicNameValuePair("userId", userId);
            list.add(param1);
            list.add(param2);
            list.add(param3);

            UrlEncodedFormEntity entityParam = new UrlEncodedFormEntity(list, "UTF-8");
            httpPost.setEntity(entityParam);
            httpPost.addHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.7.6)");
            httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
            httpPost.addHeader(xApiKeyName, xApiKeyValue);
            httpPost.addHeader(userIdName, userIdValue);

            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            entityStr = EntityUtils.toString(entity, "UTF-8");
        } catch (ClientProtocolException e) {
            System.err.println("ClientProtocolException");
            e.printStackTrace();
        } catch (ParseException e) {
            System.err.println("ParseException");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("IOException");
            e.printStackTrace();
        } finally {
            if (null != response) {
                try {
                    response.close();
                    httpClient.close();
                } catch (IOException e) {
                    System.err.println("IOException1");
                    e.printStackTrace();
                }
            }
        }

        Map map = JSON.parseObject(entityStr, Map.class);
        if (map.get("code").equals("FAIL")) {
            System.out.println("the member have already in the team");
            return false;
        } else {
            System.out.println("member have been added in team successfully.");
            return true;
        }
    }

    //userIds: ["79", "85", "86"]  code : OK
    public static boolean getTeamMember(String teamId) {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        String entityStr = null;
        CloseableHttpResponse response = null;

        try {
            //create url and add parameter
            URIBuilder uriBuilder = new URIBuilder("http://www.notexponential.com/aip2pgaming/api/index.php");
            List<NameValuePair> list = new LinkedList<NameValuePair>();
            BasicNameValuePair param1 = new BasicNameValuePair("type", "team");
            BasicNameValuePair param2 = new BasicNameValuePair("teamId", teamId);
            list.add(param1);
            list.add(param2);
            uriBuilder.setParameters(list);

            HttpGet httpGet = new HttpGet(uriBuilder.build());

            //add information in the header
            httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.7.6)");
            httpGet.addHeader("Content-Type", "application/x-www-form-urlencoded");
            httpGet.addHeader(xApiKeyName, xApiKeyValue);
            httpGet.addHeader(userIdName, userIdValue);

            // execute the request
            response = httpClient.execute(httpGet);
            // get the response
            HttpEntity entity = response.getEntity();
            // transform it to the string
            entityStr = EntityUtils.toString(entity, "UTF-8");
        } catch (ClientProtocolException e) {
            System.err.println("ClientProtocolException");
            e.printStackTrace();
        } catch (ParseException e) {
            System.err.println("ParseException");
            e.printStackTrace();
        } catch (URISyntaxException e) {
            System.err.println("URISyntaxException");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("IOException");
            e.printStackTrace();
        } finally {
            if (null != response) {
                try {
                    response.close();
                    httpClient.close();
                } catch (IOException e) {
                    System.err.println("release error");
                    e.printStackTrace();
                }
            }
        }

        Map map = JSON.parseObject(entityStr, Map.class);
        if (!map.containsKey("userIds")) {
            System.out.println("the team do not exist");
            return false;
        }
        JSONArray array = (JSONArray)map.get("userIds");
        List<String> list = JSONObject.parseArray(array.toJSONString(), String.class);
        if (list.size() == 0) {
            System.out.println("there is no member in that team");
            return true;
        }
        for (String s : list) {
            System.out.print("the members are ");
            System.out.print(s + " ");
        }
        return true;
    }

    //code : OK     gameId : 1290
    //daiyanzheng
    //gameId is 1555
    public static boolean createGame(String teamId1, String teamId2, String boardSize, String target) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String entityStr = null;
        CloseableHttpResponse response = null;

        try {
            HttpPost httpPost = new HttpPost("http://www.notexponential.com/aip2pgaming/api/index.php");

            List<NameValuePair> list = new LinkedList<NameValuePair>();
            BasicNameValuePair param1 = new BasicNameValuePair("teamId1", teamId1);
            BasicNameValuePair param2 = new BasicNameValuePair("teamId2", teamId2);
            BasicNameValuePair param3 = new BasicNameValuePair("type", "game");
            BasicNameValuePair param4 = new BasicNameValuePair("gameType", "TTT");
            BasicNameValuePair param5 = new BasicNameValuePair("boardSize", boardSize);
            BasicNameValuePair param6 = new BasicNameValuePair("target", target);
            list.add(param1);
            list.add(param2);
            list.add(param3);
            list.add(param4);
            list.add(param5);
            list.add(param6);
            UrlEncodedFormEntity entityParam = new UrlEncodedFormEntity(list, "UTF-8");
            httpPost.setEntity(entityParam);


            httpPost.addHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.7.6)");
            httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
            httpPost.addHeader(xApiKeyName, xApiKeyValue);
            httpPost.addHeader(userIdName, userIdValue);

            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            entityStr = EntityUtils.toString(entity, "UTF-8");
        } catch (ClientProtocolException e) {
            System.err.println("ClientProtocolException");
            e.printStackTrace();
        } catch (ParseException e) {
            System.err.println("ParseException");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("IOException");
            e.printStackTrace();
        } finally {
            if (null != response) {
                try {
                    response.close();
                    httpClient.close();
                } catch (IOException e) {
                    System.err.println("release error");
                    e.printStackTrace();
                }
            }
        }

        Map map = JSON.parseObject(entityStr, Map.class);
        if (map.get("code").equals("FAIL")) {
            System.out.println("the teamId is wrong");
            return false;
        } else {
            gameId = String.valueOf(map.get("gameId"));
            System.out.println("game created successfully, and the gameId is " + gameId);
            return true;
        }
    }

    //moveId : 1017   code : OK
    //{"code":"FAIL","message":"Cannot make move - It is not the turn of team: 1128"}
    public static boolean makeMove(String gameId, String teamId, String move) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String entityStr = null;
        CloseableHttpResponse response = null;

        try {
            HttpPost httpPost = new HttpPost("http://www.notexponential.com/aip2pgaming/api/index.php");

            List<NameValuePair> list = new LinkedList<NameValuePair>();
            BasicNameValuePair param1 = new BasicNameValuePair("gameId", gameId);
            BasicNameValuePair param2 = new BasicNameValuePair("type", "move");
            BasicNameValuePair param3 = new BasicNameValuePair("teamId", teamId);
            BasicNameValuePair param4 = new BasicNameValuePair("move", move);
            list.add(param1);
            list.add(param2);
            list.add(param3);
            list.add(param4);
            UrlEncodedFormEntity entityParam = new UrlEncodedFormEntity(list, "UTF-8");
            httpPost.setEntity(entityParam);

            httpPost.addHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.7.6)");
            httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
            httpPost.addHeader(xApiKeyName, xApiKeyValue);
            httpPost.addHeader(userIdName, userIdValue);

            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            entityStr = EntityUtils.toString(entity, "UTF-8");
        } catch (ClientProtocolException e) {
            System.err.println("ClientProtocolException");
            e.printStackTrace();
        } catch (ParseException e) {
            System.err.println("ParseException");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("IOException");
            e.printStackTrace();
        } finally {
            if (null != response) {
                try {
                    response.close();
                    httpClient.close();
                } catch (IOException e) {
                    System.err.println("release error");
                    e.printStackTrace();
                }
            }
        }
        Map map = JSON.parseObject(entityStr, Map.class);
        if (map.get("code").equals("FAIL")) {
            System.out.println(map.get("message"));
            return false;
        } else {
            moveId = String.valueOf(map.get("moveId"));
            System.out.println("move successfully and the moveId is " + moveId);
            return true;
        }
    }

    public static boolean getMoves(String gameId) {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        String entityStr = null;
        CloseableHttpResponse response = null;

        try {
            //create url and add parameter
            URIBuilder uriBuilder = new URIBuilder("http://www.notexponential.com/aip2pgaming/api/index.php");
            List<NameValuePair> list = new LinkedList<NameValuePair>();
            BasicNameValuePair param1 = new BasicNameValuePair("count", "1");
            BasicNameValuePair param2 = new BasicNameValuePair("type", "moves");
            BasicNameValuePair param3 = new BasicNameValuePair("gameId", gameId);
            list.add(param1);
            list.add(param2);
            list.add(param3);
            uriBuilder.setParameters(list);

            HttpGet httpGet = new HttpGet(uriBuilder.build());

            //add information in the header
            httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.7.6)");
            httpGet.addHeader("Content-Type", "application/x-www-form-urlencoded");
            httpGet.addHeader(xApiKeyName, xApiKeyValue);
            httpGet.addHeader(userIdName, userIdValue);

            // execute the request
            response = httpClient.execute(httpGet);
            // get the response
            HttpEntity entity = response.getEntity();
            // transform it to the string
            entityStr = EntityUtils.toString(entity, "UTF-8");
        } catch (ClientProtocolException e) {
            System.err.println("ClientProtocolException");
            e.printStackTrace();
        } catch (ParseException e) {
            System.err.println("ParseException");
            e.printStackTrace();
        } catch (URISyntaxException e) {
            System.err.println("URISyntaxException");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("IOException");
            e.printStackTrace();
        } finally {
            if (null != response) {
                try {
                    response.close();
                    httpClient.close();
                } catch (IOException e) {
                    System.err.println("release error");
                    e.printStackTrace();
                }
            }
        }
//{"moves":[{"moveId":"9238","gameId":"1556","teamId":"1129","move":"0,1","symbol":"X","moveX":"0","moveY":"1"}],"code":"OK"}
        Map map = JSON.parseObject(entityStr, Map.class);
        if (!map.containsKey("moves")) {
            System.out.println("the gameId is wrong");
            return false;
        }
        JSONArray array = (JSONArray)map.get("moves");
        List<String> list = JSONObject.parseArray(array.toJSONString(), String.class);
        String recentMove = list.get(0);
        Map map1 = JSON.parseObject(recentMove, Map.class);
        String lastTeamId = (String)map1.get("teamId");
        if (lastTeamId.equals(teamId)) {
            System.out.println("the opponent have not yet move");
            return false;
        }
        move = (String)map1.get("move");
        System.out.println("oppents move is " + move);
        return true;
    }

    public static void getBoardString(String gameId) {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        String entityStr = null;
        CloseableHttpResponse response = null;

        try {
            //create url and add parameter
            URIBuilder uriBuilder = new URIBuilder("http://www.notexponential.com/aip2pgaming/api/index.php");
            List<NameValuePair> list = new LinkedList<NameValuePair>();
            BasicNameValuePair param1 = new BasicNameValuePair("gameId", gameId);
            BasicNameValuePair param2 = new BasicNameValuePair("type", "boardString");
            //BasicNameValuePair param3 = new BasicNameValuePair("gameId", "1310");
            list.add(param1);
            list.add(param2);
            //list.add(param3);
            uriBuilder.setParameters(list);

            HttpGet httpGet = new HttpGet(uriBuilder.build());

            //add information in the header
            httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.7.6)");
            httpGet.addHeader("Content-Type", "application/x-www-form-urlencoded");
            httpGet.addHeader("x-api-key", "6f9a0690dbb568ca5333");
            httpGet.addHeader("userId", "778");

            // execute the request
            response = httpClient.execute(httpGet);
            // get the response
            HttpEntity entity = response.getEntity();
            // transform it to the string
            entityStr = EntityUtils.toString(entity, "UTF-8");
        } catch (ClientProtocolException e) {
            System.err.println("ClientProtocolException");
            e.printStackTrace();
        } catch (ParseException e) {
            System.err.println("ParseException");
            e.printStackTrace();
        } catch (URISyntaxException e) {
            System.err.println("URISyntaxException");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("IOException");
            e.printStackTrace();
        } finally {
            if (null != response) {
                try {
                    response.close();
                    httpClient.close();
                } catch (IOException e) {
                    System.err.println("释放连接出错");
                    e.printStackTrace();
                }
            }
        }
        // 打印响应内容
        System.out.println(entityStr);
    }

    public static void getBoardMap(String gameId) {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        String entityStr = null;
        CloseableHttpResponse response = null;

        try {
            //create url and add parameter
            URIBuilder uriBuilder = new URIBuilder("http://www.notexponential.com/aip2pgaming/api/index.php");
            List<NameValuePair> list = new LinkedList<NameValuePair>();
            BasicNameValuePair param1 = new BasicNameValuePair("type", "boardMap");
            BasicNameValuePair param2 = new BasicNameValuePair("gameId", gameId);
            list.add(param1);
            list.add(param2);
            uriBuilder.setParameters(list);

            HttpGet httpGet = new HttpGet(uriBuilder.build());

            //add information in the header
            httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.7.6)");
            httpGet.addHeader("Content-Type", "application/x-www-form-urlencoded");
            httpGet.addHeader(xApiKeyName, xApiKeyValue);
            httpGet.addHeader(userIdName, userIdValue);

            // execute the request
            response = httpClient.execute(httpGet);
            // get the response
            HttpEntity entity = response.getEntity();
            // transform it to the string
            entityStr = EntityUtils.toString(entity, "UTF-8");
        } catch (ClientProtocolException e) {
            System.err.println("ClientProtocolException");
            e.printStackTrace();
        } catch (ParseException e) {
            System.err.println("ParseException");
            e.printStackTrace();
        } catch (URISyntaxException e) {
            System.err.println("URISyntaxException");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("IOException");
            e.printStackTrace();
        } finally {
            if (null != response) {
                try {
                    response.close();
                    httpClient.close();
                } catch (IOException e) {
                    System.err.println("release error");
                    e.printStackTrace();
                }
            }
        }
        System.out.println(entityStr);
    }






}
