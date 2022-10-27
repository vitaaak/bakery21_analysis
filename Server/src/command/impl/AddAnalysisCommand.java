package command.impl;

        import command.Command;
        import cooperation.ClientRequest;
        import cooperation.ServerResponse;
        import entity.Analysis;
        import org.codehaus.jackson.map.ObjectMapper;
        import service.Service;
        import service.impl.ServiceImpl;

        import java.io.IOException;

public class AddAnalysisCommand implements Command {
    private ClientRequest request;
    private ServerResponse response;
    private Service service;

    public AddAnalysisCommand(ClientRequest request, ServerResponse response) {
        this.request = request;
        this.response = response;
        this.service = ServiceImpl.getInstance();
    }

    @Override
    public ServerResponse execute() {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonAnalysis = request.getData().toString();
            Analysis analysis = objectMapper.readValue(jsonAnalysis, Analysis.class);
            System.out.println(analysis.getAlternative1());
            boolean result = service.addAnalysis(analysis);
            response.setSuccess(result);
            return response;
        } catch (IOException e) {
            response.setSuccess(false);
            return response;
        }
    }
}