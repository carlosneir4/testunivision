package co.com.univision.widgets.service;

import co.com.univision.widgets.domain.WidgetDTO;
import rx.Observable;

/**
 * Interface
 */
public interface UnivisionWidgetsService {

    /**
     * Fetches list of widgets from an external resource.
     * @return
     */
    Observable<WidgetDTO> getWidgets(String path);
}
