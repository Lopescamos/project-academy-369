package com.olimpiadas369.backend.exportacion.application.internal.commandservices;

import com.olimpiadas369.backend.exportacion.application.commandservices.ExportacionCommandService;
import com.olimpiadas369.backend.exportacion.application.internal.outboundservices.acl.ExternalRegistroReader;
import com.olimpiadas369.backend.exportacion.domain.model.commands.ExportarRegistrosBloqueCommand;
import com.olimpiadas369.backend.exportacion.infrastructure.excel.ExcelPorBloqueGenerator;
import com.olimpiadas369.backend.shared.application.result.ApplicationError;
import com.olimpiadas369.backend.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class ExportacionCommandServiceImpl implements ExportacionCommandService {

    private final ExternalRegistroReader registros;
    private final ExcelPorBloqueGenerator generator;

    public ExportacionCommandServiceImpl(ExternalRegistroReader registros, ExcelPorBloqueGenerator generator) {
        this.registros = registros; this.generator = generator;
    }

    @Override
    public Result<byte[], ApplicationError> handle(ExportarRegistrosBloqueCommand c) {
        try {
            var confirmados = registros.registrosConfirmados(c.bloqueId());
            if (confirmados.isEmpty())
                return Result.failure(ApplicationError.businessRuleViolation("Exportacion",
                        "No confirmed records for bloque " + c.bloqueId()));
            return Result.success(generator.generar(c.bloqueId(), confirmados));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("exportar-registros-bloque", e.getMessage()));
        }
    }
}
